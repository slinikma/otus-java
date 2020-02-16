package ru.otus.hw15.types;

import lombok.Getter;
import ru.otus.hw15.visitor.Visitor;
import ru.otus.hw15.visitor.TraversedField;

import javax.json.JsonValue;
import java.lang.reflect.Field;

public class StringField extends TraversedField {

  @Getter private final String value;

  public StringField(Field field, Object value) {
    super(field);
    this.value = (String) value;
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visit(this);
  }
}
