package ru.otus.hw15.types;

import ru.otus.hw15.visitor.Visitor;
import ru.otus.hw15.visitor.TraversedField;

import javax.json.JsonObject;
import java.lang.reflect.Field;

public class ArrayFiled extends TraversedField {
  private final Object array;

  public ArrayFiled(Field field, Object array) {
    super(field);
    this.array = array;
  }

  @Override
  public JsonObject accept(Visitor visitor) throws ClassNotFoundException, NoSuchMethodException {
    return visitor.visit(this);
  }

  public Object getArray() {
    return array;
  }
}
