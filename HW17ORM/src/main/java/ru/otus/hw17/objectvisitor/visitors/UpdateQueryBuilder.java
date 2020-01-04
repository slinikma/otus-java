package ru.otus.hw17.objectvisitor.visitors;

import ru.otus.hw17.annotations.Id;
import ru.otus.hw17.objectvisitor.Visitor;
import ru.otus.hw17.objectvisitor.visitable.types.ArrayField;
import ru.otus.hw17.objectvisitor.visitable.types.ObjectField;
import ru.otus.hw17.objectvisitor.visitable.types.PrimitiveField;
import ru.otus.hw17.objectvisitor.visitable.types.StringField;

public class UpdateQueryBuilder implements Visitor {

  private StringBuilder query;
  private boolean isTableSet = false;
  private String idFieldName = null;
  private String idFieldValue = null;
  private boolean isCommaNeeded = false;

  public UpdateQueryBuilder() {
    query = new StringBuilder();
    query.append("update ");
  }

  @Override
  public void visit(ArrayField field) throws ClassNotFoundException, NoSuchMethodException {
    if (field.isAnnotationPresent(Id.class)) {
      throw new IllegalArgumentException("Array can't be an field id!");
    }

    if (!isTableSet) {
      query.append(field.getFieldOfObject().getClass().getSimpleName())
          .append(" set ");
      isTableSet = true;
    }

    if (isCommaNeeded) {
      query.append(", ");
    } else {
      isCommaNeeded = true;
    }

    query.append(field.getName())
        .append(" = ")
        .append(field.getArray());
  }

  @Override
  public void visit(PrimitiveField field) throws NoSuchMethodException {

    if (!isTableSet) {
      query.append(field.getFieldOfObject().getClass().getSimpleName())
          .append(" set ");
      isTableSet = true;
    }

    if (field.isAnnotationPresent(Id.class)) {
      idFieldName = field.getName();
      idFieldValue = field.getBoxedPrimitive().toString();
    } else {
      if (isCommaNeeded) {
        query.append(", ");
      } else {
        isCommaNeeded = true;
      }

      query.append(field.getName())
          .append(" = ")
          .append(field.getBoxedPrimitive());
    }
  }

  @Override
  public void visit(ObjectField field) {
    // Сложное не примитивное поле, которое должно быть ссылкой на другую таблицу.
    // Выходит за рамки ДЗ
    if (!isTableSet) {
      query.append(field.getFieldOfObject().getClass().getSimpleName())
          .append(" set ");
      isTableSet = true;
    } else {
      throw new UnsupportedOperationException("Object fields unsupported!");
    }
  }

  @Override
  public void visit(StringField field) {

    if (!isTableSet) {
      query.append(field.getFieldOfObject().getClass().getSimpleName())
          .append(" set ");
      isTableSet = true;
    }

    if (field.isAnnotationPresent(Id.class)) {
      idFieldName = field.getName();
      idFieldValue = field.getValue();
    } else {
      if (isCommaNeeded) {
        query.append(", ");
      } else {
        isCommaNeeded = true;
      }

      query.append(field.getName())
          .append(" = ")
          .append("'")
          .append(field.getValue())
          .append("'");
    }
  }

  public String getQuery() {
    query.append(" where ")
        .append(idFieldName)
        .append(" = ")
        .append(idFieldValue);

    return query.toString();
  }
}
