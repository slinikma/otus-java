package ru.otus.hw15.types;

import ru.otus.hw15.visitor.TraversedField;
import ru.otus.hw15.visitor.Visitor;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.lang.reflect.Field;
import java.util.Collection;

public class CollectionFiled extends TraversedField {
  private final Collection collection;

  public CollectionFiled(Field field, Collection collection) {
    super(field);
    this.collection = collection;
  }

  @Override
  public JsonArray accept(Visitor visitor) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException {
    return visitor.visit(this);
  }

  public Collection getCollection() {
    return collection;
  }
}
