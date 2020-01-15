package ru.otus.hw17.objectvisitor.visitable.types;

import lombok.Getter;
import ru.otus.hw17.objectvisitor.TraversedField;
import ru.otus.hw17.objectvisitor.Visitor;

import java.lang.reflect.Field;

public class ObjectField extends TraversedField {
  @Getter private Object fieldOfObject;

  public ObjectField(Field field, Object obj) throws NoSuchMethodException {
    super(obj.getClass(), field);
    this.fieldOfObject = obj;
  }

  @Override
  public TraversedField setObject(Object obj) throws IllegalAccessException {
    this.fieldOfObject = obj;
    return this;
  }

  @Override
  public TraversedField accept(Visitor visitor) throws NoSuchMethodException {
    visitor.visit(this);
    return this;
  }
}
