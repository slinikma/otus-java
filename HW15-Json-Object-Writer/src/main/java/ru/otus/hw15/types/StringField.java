package ru.otus.hw15.types;

import ru.otus.hw15.visitor.Visitor;
import ru.otus.hw15.visitor.TraversedField;

import javax.json.JsonObject;
import javax.json.JsonValue;
import java.lang.reflect.Field;

public class StringField extends TraversedField {

  String value;

  public StringField(Field field, Object value) {
    super(field);
    this.value = (String) value;
  }

  @Override
  public JsonObject accept(Visitor visitor) {
    return visitor.visit(this);
  }

  public String get() {
    return value;
  }
}
