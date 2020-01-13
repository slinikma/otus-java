package ru.otus.hw17.objectvisitor.visitors;

import ru.otus.hw17.annotations.TraverserSkip;
import ru.otus.hw17.objectvisitor.Visitor;
import ru.otus.hw17.objectvisitor.visitable.types.ArrayField;
import ru.otus.hw17.objectvisitor.visitable.types.ObjectField;
import ru.otus.hw17.objectvisitor.visitable.types.PrimitiveField;
import ru.otus.hw17.objectvisitor.visitable.types.StringField;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetObjectLoader implements Visitor {

  ResultSet resultSet;

  public ResultSetObjectLoader(ResultSet resultSet) {
    this.resultSet = resultSet;
  }

  @Override
  public void visit(ArrayField field) {

    // Пропускаем поля из ДЗ Hibernate
    if (field.isAnnotationPresent(TraverserSkip.class)) {
      return;
    }

    try {
      Object fieldValue = resultSet.getArray(field.getName());
      field.getField().setAccessible(true);
      field.getField().set(field.getFieldOfObject(), fieldValue);
    } catch (SQLException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void visit(PrimitiveField field) {

    // Пропускаем поля из ДЗ Hibernate
    if (field.isAnnotationPresent(TraverserSkip.class)) {
      return;
    }

    try {
      field.getField().setAccessible(true);

      // TODO: можно как-то проще сделать???
      if (field.getBoxedPrimitive().getClass() == Boolean.class) {
        boolean fieldValue = resultSet.getBoolean(field.getName());
        field.getField().set(field.getFieldOfObject(), fieldValue);
      } else if (field.getBoxedPrimitive().getClass() == Byte.class) {
        byte fieldValue = resultSet.getByte(field.getName());
        field.getField().set(field.getFieldOfObject(), fieldValue);
      } else if (field.getBoxedPrimitive().getClass() == Short.class) {
        short fieldValue = resultSet.getShort(field.getName());
        field.getField().set(field.getFieldOfObject(), fieldValue);
      } else if (field.getBoxedPrimitive().getClass() == Integer.class) {
        int fieldValue = resultSet.getInt(field.getName());
        field.getField().set(field.getFieldOfObject(), fieldValue);
      } else if (field.getBoxedPrimitive().getClass() == Long.class) {
        long fieldValue = resultSet.getLong(field.getName());
        field.getField().set(field.getFieldOfObject(), fieldValue);
      } else if (field.getBoxedPrimitive().getClass() == Float.class) {
        float fieldValue = resultSet.getFloat(field.getName());
        field.getField().set(field.getFieldOfObject(), fieldValue);
      } else if (field.getBoxedPrimitive().getClass() == Double.class) {
        double fieldValue = resultSet.getDouble(field.getName());
        field.getField().set(field.getFieldOfObject(), fieldValue);
      }
    } catch (SQLException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void visit(ObjectField field) {

    // Пропускаем поля из ДЗ Hibernate
    if (field.isAnnotationPresent(TraverserSkip.class)) {
      return;
    }

    // Сложное не примитивное поле, которое должно быть ссылкой на другую таблицу.
    // Выходит за рамки ДЗ
    throw new UnsupportedOperationException("Object fields unsupported!");
  }

  @Override
  public void visit(StringField field) {

    // Пропускаем поля из ДЗ Hibernate
    if (field.isAnnotationPresent(TraverserSkip.class)) {
      return;
    }

    try {
      String stringFieldValue = resultSet.getString(field.getName());
      field.getField().setAccessible(true);
      field.getField().set(field.getFieldOfObject(), stringFieldValue);
    } catch (SQLException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }
}
