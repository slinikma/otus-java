package ru.otus.hw17.objectvisitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.otus.hw17.annotations.TraverserSkip;
import ru.otus.hw17.objectvisitor.visitable.types.ArrayField;
import ru.otus.hw17.objectvisitor.visitable.types.ObjectField;
import ru.otus.hw17.objectvisitor.visitable.types.PrimitiveField;
import ru.otus.hw17.objectvisitor.visitable.types.StringField;
import ru.otus.hw17.objectvisitor.visitors.ResultSetObjectLoader;

import ru.otus.hw21.Cache;
import ru.otus.hw21.CacheListener;
import ru.otus.hw21.MyCache;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

public class Traverser {

  private static final Logger logger = LoggerFactory.getLogger(Traverser.class);

  private static Cache<Class, List<TraversedField>> traversedFieldsCache = new MyCache();

  static {
    CacheListener<Class, List<TraversedField>> сacheListener =
        (key, value, action) -> logger.info("key:{}, value:{}, action: {}",  key, value, action);
    traversedFieldsCache.addListener(сacheListener);
  }

  public static <T> T loadObjectFromResultSet(ResultSet resultSet, T object) throws ObjectTraverseException {
    var resultSetObjectLoader = new ResultSetObjectLoader(resultSet);
    traverse(object, resultSetObjectLoader, null);

    return object;
  }

  public static void traverse(Object object, Visitor service, Field rootField) throws ObjectTraverseException {

    List<TraversedField> traversedFields = traversedFieldsCache.get(object.getClass());
    if (traversedFields != null) {
      for (var field : traversedFields) {
        try {
          field.setObject(object).accept(service);
        } catch (Exception e) {
          throw new ObjectTraverseException(e);
        }
      }
    } else {

      traversedFields = new ArrayList<>();

      // Обрабатываем сам объект и его поле, если указали
      if (rootField != null && !rootField.isAnnotationPresent(TraverserSkip.class)) {
        try {
          if (object.getClass().isArray()) {
            traversedFields.add(new ArrayField(rootField, object).accept(service));
          } else {
            traversedFields.add(new ObjectField(rootField, object).accept(service));
          }
        } catch (Exception ex) {
          throw new ObjectTraverseException(ex);
        }
      }

      // Обрабатываем поля объекта
      Field[] fields = object.getClass().getDeclaredFields();
      for (Field field : fields) {
        // Пропускаем поля из ДЗ Hibernate
        if (field.isAnnotationPresent(TraverserSkip.class)) {
          continue;
        }

        try {
          field.setAccessible(true);
          if (Modifier.isStatic(field.getModifiers())) {
            continue;
          }
          if (field.getType().isPrimitive()) {
            traversedFields.add(new PrimitiveField(field, object).accept(service));
          } else if (field.getType().isArray()) {
            traversedFields.add(new ArrayField(field, object).accept(service));
          } else if (field.getType().isAssignableFrom(String.class)) {
            traversedFields.add(new StringField(field, object).accept(service));
          } else {
            traverse(field.get(object), service, field);
          }
        } catch (Exception ex) {
          throw new ObjectTraverseException(ex);
        }
      }

      // TODO: т.к. ключ - класс, то смысла от WeakHashMap никакого нет? то-же самое что и обычный HashMap?
      traversedFieldsCache.put(object.getClass(), traversedFields);
    }
  }
}
