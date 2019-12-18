package ru.otus.hw17.objectvisitor.visitable.types;

import lombok.Getter;
import ru.otus.hw17.objectvisitor.TraversedField;
import ru.otus.hw17.objectvisitor.Visitor;

import java.lang.reflect.Field;

public class ObjectField extends TraversedField {
  @Getter private Object fieldOfObject;

  public ObjectField(Field field, Object obj) {
    super(field);
    this.fieldOfObject = obj;
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visit(this);
  }
}
