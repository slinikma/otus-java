package ru.otus.hw1719.objectvisitor.visitable.types;

import lombok.Getter;
import ru.otus.hw1719.objectvisitor.TraversedField;
import ru.otus.hw1719.objectvisitor.Visitor;

import java.lang.reflect.Field;

public class PrimitiveField extends TraversedField {
  @Getter private Object boxedPrimitive;
  @Getter private Object fieldOfObject;

  public PrimitiveField(Field field, Object obj) throws IllegalAccessException {
    super(field);
    this.fieldOfObject = obj;
    this.boxedPrimitive = field.get(obj);
  }

  @Override
  public void accept(Visitor visitor) throws NoSuchMethodException {
    visitor.visit(this);
  }
}
