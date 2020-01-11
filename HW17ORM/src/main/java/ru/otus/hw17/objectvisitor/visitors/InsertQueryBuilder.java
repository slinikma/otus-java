package ru.otus.hw17.objectvisitor.visitors;

import lombok.Getter;
import ru.otus.hw17.annotations.Id;
import ru.otus.hw17.objectvisitor.TraversedField;
import ru.otus.hw17.objectvisitor.Visitor;
import ru.otus.hw17.objectvisitor.visitable.types.ArrayField;
import ru.otus.hw17.objectvisitor.visitable.types.ObjectField;
import ru.otus.hw17.objectvisitor.visitable.types.PrimitiveField;
import ru.otus.hw17.objectvisitor.visitable.types.StringField;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InsertQueryBuilder implements Visitor {

  // Сохраняем разобранный класс
  @Getter private List<TraversedField> fieldList;
  @Getter private String idFieldName = null;
  @Getter private Object idFieldValue = null;
  @Getter private Constructor classConstructor = null;
  @Getter private String className = null;

  // Сохраняем производные от разобранного класса
  @Getter private List<Object> params;
  private StringBuilder query;

  private boolean isTableAppended = false;
  private boolean isCommaNeeded = false;

  public InsertQueryBuilder() {
    this.params = new ArrayList<>();
    this.fieldList = new ArrayList<>();

    this.query = new StringBuilder();
    this.query.append("insert into ");
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

    if (field.isAnnotationPresent(Id.class)) {
      throw new IllegalArgumentException("Array can't be an field id!");
    }

    // Формируем SQL запрос
    if (!this.isTableAppended) {
      query.append(field.getFieldOfObject().getClass().getSimpleName())
          .append("(");
      this.isTableAppended = true;
    }

    if (isCommaNeeded) {
      query.append(",");
    }

    query.append(field.getName());

    // Сохраняем параметры SQL запроса
    // TODO: возможно бессмысленно, т.к. уже сохранил поля объекта
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

    if (field.isAnnotationPresent(Id.class)) {
      // Сохраняем поле id
      this.idFieldName = field.getName();
      this.idFieldValue = field.getBoxedPrimitive();
    }

    // Формируем SQL запрос
    if (!this.isTableAppended) {
      query.append(field.getFieldOfObject().getClass().getSimpleName())
          .append("(");
      this.isTableAppended = true;
    }

    if (isCommaNeeded) {
      query.append(",");
    } else {
      isCommaNeeded = true;
    }

    query.append(field.getName());

    // Сохраняем параметры SQL запроса
    // TODO: возможно бессмысленно, т.к. уже сохранил поля объекта
    params.add(field.getBoxedPrimitive());
  }

  @Override
  public void visit(ObjectField field) throws NoSuchMethodException {
    // Сохраняем поля
    fieldList.add(field);

    // Сложное не примитивное поле, которое должно быть ссылкой на другую таблицу.
    // Выходит за рамки ДЗ
    if (className == null) {
      // Сохраняем имя класса
      className = field.getFieldOfObject().getClass().getSimpleName();
      classConstructor = field.getFieldOfObject().getClass().getConstructor();
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

    if (field.isAnnotationPresent(Id.class)) {
      // Сохраняем поле id
      this.idFieldName = field.getName();
      this.idFieldValue = field.getValue();
    }

    // Формируем SQL запрос
    if (!this.isTableAppended) {
      query.append(field.getFieldOfObject().getClass().getSimpleName())
          .append("(");
      this.isTableAppended = true;
    }

    if (isCommaNeeded) {
      query.append(",");
    }

    query.append(field.getName());

    // Сохраняем параметры SQL запроса
    // TODO: возможно бессмысленно, т.к. уже сохранил поля объекта
    params.add(field.getValue());
  }

  public String getQueryString() {
    boolean isFirstParam = true;

    query.append(") values (");

    // Цикл для формирования SQL строки с нужным количеством '?'
    for (var param: params) {
      if (!isFirstParam) {
        query.append(",");
      } else {
        isFirstParam = false;
      }

      query.append(param);
    }
    query.append(")");

    return query.toString();
  }
}
