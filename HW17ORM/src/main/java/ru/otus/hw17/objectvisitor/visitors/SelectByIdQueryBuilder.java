package ru.otus.hw17.objectvisitor.visitors;

import ru.otus.hw17.annotations.Id;
import ru.otus.hw17.objectvisitor.Visitor;
import ru.otus.hw17.objectvisitor.visitable.types.ArrayField;
import ru.otus.hw17.objectvisitor.visitable.types.ObjectField;
import ru.otus.hw17.objectvisitor.visitable.types.PrimitiveField;
import ru.otus.hw17.objectvisitor.visitable.types.StringField;

public class SelectByIdQueryBuilder implements Visitor {

  private StringBuilder query;
  private String tableName = null;
  private String idFieldName = null;
  private boolean isCommaNeeded = false;

  public SelectByIdQueryBuilder() {
    query = new StringBuilder();
    query.append("select");
  }

  @Override
  public void visit(ArrayField field) throws ClassNotFoundException, NoSuchMethodException {
    if (field.isAnnotationPresent(Id.class)) {
      throw new IllegalArgumentException("Array can't be an field id!");
    }

    if (tableName == null) {
      tableName = field.getFieldOfObject().getClass().getSimpleName();
    }

    if (isCommaNeeded) {
      query.append(",");
    } else {
      isCommaNeeded = true;
    }

    query.append(" ")
        .append(field.getName());
  }

  @Override
  public void visit(PrimitiveField field) throws NoSuchMethodException {
    if (field.isAnnotationPresent(Id.class)) {
      idFieldName = field.getName();
    }

    if (tableName == null) {
      tableName = field.getFieldOfObject().getClass().getSimpleName();
    }

    if (isCommaNeeded) {
      query.append(",");
    } else {
      isCommaNeeded = true;
    }

    query.append(" ")
        .append(field.getName());
  }

  @Override
  public void visit(ObjectField field) {
    // Сложное не примитивное поле, которое должно быть ссылкой на другую таблицу.
    // Выходит за рамки ДЗ
    if (tableName == null) {
      tableName = field.getFieldOfObject().getClass().getSimpleName();
    } else {
      throw new UnsupportedOperationException("Object fields unsupported!");
    }
  }

  @Override
  public void visit(StringField field) {
    if (field.isAnnotationPresent(Id.class)) {
      idFieldName = field.getName();
    }

    if (tableName == null) {
      tableName = field.getFieldOfObject().getClass().getSimpleName();
    }

    if (isCommaNeeded) {
      query.append(",");
    } else {
      isCommaNeeded = true;
    }

    query.append(" ")
        .append(field.getName());
  }

  public String getQuery() {
    query.append(" from ")
        .append(tableName)
        .append(" where ")
        .append(idFieldName)
        .append(" = ?");

    return query.toString();
  }
}
