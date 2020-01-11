package ru.otus.hw17.objectvisitor;

import ru.otus.hw17.objectvisitor.visitable.types.ArrayField;
import ru.otus.hw17.objectvisitor.visitable.types.ObjectField;
import ru.otus.hw17.objectvisitor.visitable.types.PrimitiveField;
import ru.otus.hw17.objectvisitor.visitable.types.StringField;
import ru.otus.hw17.objectvisitor.visitors.InsertQueryBuilder;
import ru.otus.hw17.objectvisitor.visitors.SelectByIdQueryBuilder;
import ru.otus.hw17.objectvisitor.visitors.ResultSetObjectLoader;
import ru.otus.hw17.objectvisitor.visitors.UpdateQueryBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Traverser {

  public static <T> T loadObjectFromResultSet(ResultSet resultSet, T object) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException {
    var resultSetObjectLoader = new ResultSetObjectLoader(resultSet);
    traverse(object, resultSetObjectLoader, null);

    return object;
  }

  public static void traverse(Object object, Visitor service, Field rootField) throws IllegalAccessException, ClassNotFoundException, NoSuchMethodException {
    // Обрабатываем сам объект и его поле, если указали
    if (rootField != null) {
      if (object.getClass().isArray()) {
        new ArrayField(rootField, object).accept(service);
      } else {
        new ObjectField(rootField, object).accept(service);
      }
    }

    // Обрабатываем поля объекта
    Field[] fields = object.getClass().getDeclaredFields();
    for (Field field : fields) {
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
    }
  }
}
