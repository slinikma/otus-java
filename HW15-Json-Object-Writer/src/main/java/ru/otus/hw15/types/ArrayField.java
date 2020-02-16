package ru.otus.hw15.types;

import lombok.Getter;
import ru.otus.hw15.visitor.Visitor;
import ru.otus.hw15.visitor.TraversedField;

import java.lang.reflect.Field;

public class ArrayField extends TraversedField {
  @Getter private final Object array;

  public ArrayField(Field field, Object array) {
    super(field);
    this.array = array;
  }

  @Override
  public void accept(Visitor visitor) throws ClassNotFoundException, NoSuchMethodException {
    visitor.visit(this);
  }
}
