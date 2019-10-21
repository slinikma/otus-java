package ru.otus.hw15.types;

import ru.otus.hw15.visitor.Visitor;
import ru.otus.hw15.visitor.TraversedField;

import java.lang.reflect.Field;

public class TraversedArray extends TraversedField {
  private final Object array;

  public TraversedArray(Field field, Object array) {
    super(field);
    this.array = array;
  }

  @Override
  public void accept(Visitor visitor) throws ClassNotFoundException, NoSuchMethodException {
    visitor.visit(this);
  }

  public Object getArray() {
    return array;
  }
}
