package ru.otus.hw15.types;

import lombok.Getter;
import ru.otus.hw15.visitor.Visitor;
import ru.otus.hw15.visitor.TraversedField;

import java.lang.reflect.Field;

public class PrimitiveField extends TraversedField {

  @Getter private final Object boxedPrimitive;

  public PrimitiveField(Field field, Object object) {
    super(field);
    boxedPrimitive = object;
  }

  @Override
  public void accept(Visitor visitor) throws NoSuchMethodException {
    visitor.visit(this);
  }
}
