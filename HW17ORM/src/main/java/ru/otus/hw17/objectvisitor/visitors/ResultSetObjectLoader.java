package ru.otus.hw17.objectvisitor.visitors;

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

    try {
      field.getField().setAccessible(true);
      field.getField().set(field.getFieldOfObject(), resultSet.getObject(field.getName()));
    } catch (SQLException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void visit(ObjectField field) {

    // Сложное не примитивное поле, которое должно быть ссылкой на другую таблицу.
    // Выходит за рамки ДЗ
    throw new UnsupportedOperationException("Object fields unsupported!");
  }

  @Override
  public void visit(StringField field) {

    try {
      String stringFieldValue = resultSet.getString(field.getName());
      field.getField().setAccessible(true);
      field.getField().set(field.getFieldOfObject(), stringFieldValue);
    } catch (SQLException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }
}
