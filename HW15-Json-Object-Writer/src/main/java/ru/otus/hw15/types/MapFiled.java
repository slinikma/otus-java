package ru.otus.hw15.types;

import ru.otus.hw15.visitor.TraversedField;
import ru.otus.hw15.visitor.Visitor;

import javax.json.JsonObject;
import java.lang.reflect.Field;
import java.util.Map;

public class MapFiled extends TraversedField {
  private final Map map;

  public MapFiled(Field field, Map map) {
    super(field);
    this.map = map;
  }

  @Override
  public JsonObject accept(Visitor visitor) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException {
    return visitor.visit(this);
  }

  public Map getMap() {
    return map;
  }
}
