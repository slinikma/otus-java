package ru.otus.hw1719.objectvisitor.visitable.types;

import lombok.Getter;
import ru.otus.hw1719.objectvisitor.TraversedField;
import ru.otus.hw1719.objectvisitor.Visitor;

import java.lang.reflect.Field;

public class ArrayField extends TraversedField {
  @Getter private final Object array;
  @Getter private Object fieldOfObject;

  public ArrayField(Field field, Object obj) throws IllegalAccessException {
    super(field);
    this.array = field.get(obj);
    this.fieldOfObject = obj;
  }

  @Override
  public void accept(Visitor visitor) throws ClassNotFoundException, NoSuchMethodException {
    visitor.visit(this);
  }
}
