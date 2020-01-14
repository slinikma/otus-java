package ru.otus.hw17.objectvisitor;

import ru.otus.hw17.annotations.TraverserSkip;
import ru.otus.hw17.objectvisitor.visitable.types.ArrayField;
import ru.otus.hw17.objectvisitor.visitable.types.ObjectField;
import ru.otus.hw17.objectvisitor.visitable.types.PrimitiveField;
import ru.otus.hw17.objectvisitor.visitable.types.StringField;
import ru.otus.hw17.objectvisitor.visitors.ResultSetObjectLoader;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;

public class Traverser {

  public static <T> T loadObjectFromResultSet(ResultSet resultSet, T object) throws ObjectTraverseException {
    var resultSetObjectLoader = new ResultSetObjectLoader(resultSet);
    traverse(object, resultSetObjectLoader, null);

    return object;
  }

  public static void traverse(Object object, Visitor service, Field rootField) throws ObjectTraverseException {
    // Обрабатываем сам объект и его поле, если указали
    if (rootField != null && !rootField.isAnnotationPresent(TraverserSkip.class)) {
      try {
        if (object.getClass().isArray()) {
            new ArrayField(rootField, object).accept(service);
        } else {
          new ObjectField(rootField, object).accept(service);
        }
      } catch (Exception ex) {
        throw new ObjectTraverseException(ex);
      }
    }

    // Обрабатываем поля объекта
    // TODO: кэширование тут! если класс уже разбирался, сетим новый объект и вызываем сервис
    // что-то вроде WeakHashMap<object.getClass(), List<TraversedField>>
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
          new PrimitiveField(field, object).accept(service);
        } else if (field.getType().isArray()) {
          new ArrayField(field, object).accept(service);
        } else if (field.getType().isAssignableFrom(String.class)) {
          new StringField(field, object).accept(service);
        } else {
          traverse(field.get(object), service, field);
        }
      } catch (Exception ex) {
        throw new ObjectTraverseException(ex);
      }
    }
  }
}
