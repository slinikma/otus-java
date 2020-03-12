package ru.otus.hw17.objectvisitor.visitors;

import lombok.Getter;
import ru.otus.hw17.annotations.MyOrmId;
import ru.otus.hw17.objectvisitor.TraversedField;
import ru.otus.hw17.objectvisitor.Visitor;
import ru.otus.hw17.objectvisitor.visitable.types.ArrayField;
import ru.otus.hw17.objectvisitor.visitable.types.ObjectField;
import ru.otus.hw17.objectvisitor.visitable.types.PrimitiveField;
import ru.otus.hw17.objectvisitor.visitable.types.StringField;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class UpdateQueryBuilder implements Visitor {

  // Сохраняем разобранный класс
  @Getter private List<TraversedField> fieldList;
  @Getter private String idFieldName = null;
  @Getter private Object idFieldValue = null;
  @Getter private Constructor classConstructor = null;
  @Getter private String className = null;

  // Сохраняем производные от разобранного класса
  @Getter private List<Object> params;
  private StringBuilder query;

  private boolean isTableSet = false;
  private boolean isCommaNeeded = false;

  public UpdateQueryBuilder() {
    this.params = new ArrayList<>();
    this.fieldList = new ArrayList<>();

    this.query = new StringBuilder();
    this.query.append("update ");
  }

  @Override
  public void visit(ArrayField field) throws NoSuchMethodException {

    // Сохраняем поля
    fieldList.add(field);

    // Сохраняем имя класса
    if (className == null) {
      className = field.getFieldOfObject().getClass().getSimpleName();
    }

    // Сохраняем конструктор
    if (classConstructor == null) {
      classConstructor = field.getFieldOfObject().getClass().getConstructor();
    }

    if (field.isAnnotationPresent(MyOrmId.class)) {
      throw new IllegalArgumentException("Array can't be an field id!");
    }

    // Формируем SQL запрос
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
        .append("?");

    params.add(field.getArray());
  }

  @Override
  public void visit(PrimitiveField field) throws NoSuchMethodException {

    // Сохраняем поля
    fieldList.add(field);

    // Сохраняем имя класса
    if (className == null) {
      className = field.getFieldOfObject().getClass().getSimpleName();
    }

    // Сохраняем конструктор
    if (classConstructor == null) {
      classConstructor = field.getFieldOfObject().getClass().getConstructor();
    }

    // Формируем SQL запрос
    if (!isTableSet) {
      query.append(field.getFieldOfObject().getClass().getSimpleName())
          .append(" set ");
      isTableSet = true;
    }

    if (field.isAnnotationPresent(MyOrmId.class)) {
      // Сохраняем поле id
      this.idFieldName = field.getName();
      this.idFieldValue = field.getBoxedPrimitive();
    } else {
      if (isCommaNeeded) {
        query.append(", ");
      } else {
        isCommaNeeded = true;
      }

      query.append(field.getName())
          .append(" = ")
          .append("?");

      params.add(field.getBoxedPrimitive());
    }
  }

  @Override
  public void visit(ObjectField field) throws NoSuchMethodException {

    // Сохраняем поля
    fieldList.add(field);

    // Сложное не примитивное поле, которое должно быть ссылкой на другую таблицу.
    // Выходит за рамки ДЗ
    if (!isTableSet) {
      className = field.getFieldOfObject().getClass().getSimpleName();
      classConstructor = field.getFieldOfObject().getClass().getConstructor();
      query.append(field.getFieldOfObject().getClass().getSimpleName())
          .append(" set ");
      isTableSet = true;
    } else {
      throw new UnsupportedOperationException("Object fields unsupported!");
    }
  }

  @Override
  public void visit(StringField field) throws NoSuchMethodException {

    // Сохраняем поля
    fieldList.add(field);

    // Сохраняем имя класса
    if (className == null) {
      className = field.getFieldOfObject().getClass().getSimpleName();
    }

    // Сохраняем конструктор
    if (classConstructor == null) {
      classConstructor = field.getFieldOfObject().getClass().getConstructor();
    }

    // Формируем SQL запрос
    if (!isTableSet) {
      query.append(field.getFieldOfObject().getClass().getSimpleName())
          .append(" set ");
      isTableSet = true;
    }

    if (field.isAnnotationPresent(MyOrmId.class)) {
      // Сохраняем поле id
      this.idFieldName = field.getName();
      this.idFieldValue = field.getValue();
    } else {
      if (isCommaNeeded) {
        query.append(", ");
      } else {
        isCommaNeeded = true;
      }

      query.append(field.getName())
          .append(" = ")
          .append("?");

      params.add(field.getValue());
    }
  }

  public String getQueryString() {
    query.append(" where ")
        .append(idFieldName)
        .append(" = ?");

    return query.toString();
  }
}
