package ru.otus.hw17.objectvisitor.visitable.types;

import lombok.Getter;
import ru.otus.hw17.objectvisitor.TraversedField;
import ru.otus.hw17.objectvisitor.Visitor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class ArrayField extends TraversedField {
  @Getter private Object array;
  @Getter private Object fieldOfObject;

  public ArrayField(Field field, Object obj) throws IllegalAccessException, NoSuchMethodException {
    super(obj.getClass(), field);
    this.array = field.get(obj);
    this.fieldOfObject = obj;
  }

  @Override
  public TraversedField setObject(Object obj) throws IllegalAccessException {
    this.fieldOfObject = obj;
    this.array = super.getField().get(obj);
    return this;
  }

  @Override
  public TraversedField accept(Visitor visitor) throws ClassNotFoundException, NoSuchMethodException {
    visitor.visit(this);
    return this;
  }
}
