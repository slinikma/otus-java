package ru.otus.hw15.types;

import ru.otus.hw15.visitor.Visitor;
import ru.otus.hw15.visitor.TraversedField;

import java.lang.reflect.Field;

public class TraversedPrimitive extends TraversedField {

  Object boxedPrimitive;

  public TraversedPrimitive(Field field, Object object) {
    super(field);
    boxedPrimitive = object;
  }

  @Override
  public void accept(Visitor visitor) throws NoSuchMethodException {
    visitor.visit(this);
  }

  public Object get() {
    return boxedPrimitive;
  }
}
