package ru.otus.hw17.objectvisitor.visitors;

import lombok.Getter;
import ru.otus.hw17.annotations.Id;
import ru.otus.hw17.annotations.TraverserSkip;
import ru.otus.hw17.objectvisitor.TraversedField;
import ru.otus.hw17.objectvisitor.Visitor;
import ru.otus.hw17.objectvisitor.visitable.types.ArrayField;
import ru.otus.hw17.objectvisitor.visitable.types.ObjectField;
import ru.otus.hw17.objectvisitor.visitable.types.PrimitiveField;
import ru.otus.hw17.objectvisitor.visitable.types.StringField;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class SelectByIdQueryBuilder implements Visitor {

  // Сохраняем разобранный класс
  @Getter private List<TraversedField> fieldList;
  @Getter private String idFieldName = null;
  @Getter private Object idFieldValue = null;

  // Сохраняем производные от разобранного класса
  private StringBuilder query;

  private boolean isCommaNeeded = false;

  public SelectByIdQueryBuilder() {
    this.fieldList = new ArrayList<>();

    this.query = new StringBuilder();
    this.query.append("select");
  }

  @Override
  public void visit(ArrayField field) throws ClassNotFoundException, NoSuchMethodException {

    // Пропускаем поля из ДЗ Hibernate
    if (field.isAnnotationPresent(TraverserSkip.class)) {
      return;
    }

    // Сохраняем поля
    fieldList.add(field);

    if (field.isAnnotationPresent(Id.class)) {
      throw new IllegalArgumentException("Array can't be an field id!");
    }

    // Формируем SQL запрос
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

    // Пропускаем поля из ДЗ Hibernate
    if (field.isAnnotationPresent(TraverserSkip.class)) {
      return;
    }

    // Сохраняем поля
    fieldList.add(field);

    if (field.isAnnotationPresent(Id.class)) {
      // Сохраняем поле id
      this.idFieldName = field.getName();
      this.idFieldValue = field.getBoxedPrimitive();
    }

    // Формируем SQL запрос
    if (isCommaNeeded) {
      query.append(",");
    } else {
      isCommaNeeded = true;
    }

    query.append(" ")
        .append(field.getName());
  }

  @Override
  public void visit(ObjectField field) throws NoSuchMethodException {

    // Пропускаем поля из ДЗ Hibernate
    if (field.isAnnotationPresent(TraverserSkip.class)) {
      return;
    }

    // Сохраняем поля
    fieldList.add(field);

    throw new UnsupportedOperationException("Object fields unsupported!");
  }

  @Override
  public void visit(StringField field) throws NoSuchMethodException {

    // Пропускаем поля из ДЗ Hibernate
    if (field.isAnnotationPresent(TraverserSkip.class)) {
      return;
    }

    // Сохраняем поля
    fieldList.add(field);

    if (field.isAnnotationPresent(Id.class)) {
      // Сохраняем поле id
      this.idFieldName = field.getName();
      this.idFieldValue = field.getValue();
    }

    // Формируем SQL запрос
    if (isCommaNeeded) {
      query.append(",");
    } else {
      isCommaNeeded = true;
    }

    query.append(" ")
        .append(field.getName());
  }

  public String getQueryString() {
    query.append(" from ")
        .append(fieldList.get(0).getClassName())
        .append(" where ")
        .append(this.idFieldName)
        .append(" = ?");

    return query.toString();
  }
}
