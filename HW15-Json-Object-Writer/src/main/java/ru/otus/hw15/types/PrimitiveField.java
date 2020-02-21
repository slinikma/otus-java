package ru.otus.hw15.types;

import ru.otus.hw15.visitor.Visitor;
import ru.otus.hw15.visitor.TraversedField;

import javax.json.JsonObject;
import java.lang.reflect.Field;

public class PrimitiveField extends TraversedField {

  Object boxedPrimitive;

  public PrimitiveField(Field field, Object object) {
    super(field);
    boxedPrimitive = object;
  }

  @Override
  public JsonObject accept(Visitor visitor) throws NoSuchMethodException {
    return visitor.visit(this);
  }

  public Object get() {
    return boxedPrimitive;
  }
}
