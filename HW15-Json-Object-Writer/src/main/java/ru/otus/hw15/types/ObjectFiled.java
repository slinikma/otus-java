package ru.otus.hw15.types;

import ru.otus.hw15.visitor.Visitor;
import ru.otus.hw15.visitor.TraversedField;

import javax.json.JsonObject;
import java.lang.reflect.Field;

public class ObjectFiled extends TraversedField {
  public ObjectFiled(Field field) {
    super(field);
  }

  @Override
  public JsonObject accept(Visitor visitor) {
    return visitor.visit(this);
  }
}
