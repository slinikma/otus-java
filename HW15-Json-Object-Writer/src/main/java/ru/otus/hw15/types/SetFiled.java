package ru.otus.hw15.types;

import ru.otus.hw15.visitor.TraversedField;
import ru.otus.hw15.visitor.Visitor;

import javax.json.JsonObject;
import java.lang.reflect.Field;
import java.util.Set;

public class SetFiled extends TraversedField {
  private final Set set;

  public SetFiled(Field field, Set set) {
    super(field);
    this.set = set;
  }

  @Override
  public JsonObject accept(Visitor visitor) throws ClassNotFoundException, NoSuchMethodException {
//    visitor.visit(this);
    return null;
  }

  public Set getSet() {
    return set;
  }
}
