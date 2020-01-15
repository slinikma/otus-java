package ru.otus.hw17.objectvisitor.visitable.types;

import lombok.Getter;
import ru.otus.hw17.objectvisitor.TraversedField;
import ru.otus.hw17.objectvisitor.Visitor;

import java.lang.reflect.Field;

public class StringField extends TraversedField {
  @Getter private String value;
  @Getter private Object fieldOfObject;

  public StringField(Field field, Object obj) throws IllegalAccessException, NoSuchMethodException {
    super(obj.getClass(), field);
    this.fieldOfObject = obj;
    this.value = (String) field.get(obj);
  }

  @Override
  public TraversedField setObject(Object obj) throws IllegalAccessException {
    this.fieldOfObject = obj;
    this.value = (String) super.getField().get(obj);
    return this;
  }

  @Override
  public TraversedField accept(Visitor visitor) throws NoSuchMethodException {
    visitor.visit(this);
    return this;
  }
}
