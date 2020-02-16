package ru.otus.hw15.types;

import ru.otus.hw15.visitor.Visitor;
import ru.otus.hw15.visitor.TraversedField;

import java.lang.reflect.Field;

public class ObjectField extends TraversedField {
  public ObjectField(Field field) {
    super(field);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visit(this);
  }
}
