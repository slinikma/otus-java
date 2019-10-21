package ru.otus.hw15.types;

import ru.otus.hw15.visitor.Visitor;
import ru.otus.hw15.visitor.TraversedField;

import javax.json.JsonValue;
import java.lang.reflect.Field;

public class TraversedString extends TraversedField {

  String value;

  public TraversedString(Field field, Object value) {
    super(field);
    this.value = (String) value;
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visit(this);
  }

  public String get() {
    return value;
  }
}
