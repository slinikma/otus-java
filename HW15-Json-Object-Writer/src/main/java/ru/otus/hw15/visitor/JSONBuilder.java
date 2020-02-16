package ru.otus.hw15.visitor;

import ru.otus.hw15.types.ArrayField;
import ru.otus.hw15.types.ObjectField;
import ru.otus.hw15.types.PrimitiveField;
import ru.otus.hw15.types.StringField;

public class JSONBuilder implements Visitor {
  @Override
  public void visit(ArrayField value) throws ClassNotFoundException, NoSuchMethodException {

  }

  @Override
  public void visit(PrimitiveField value) throws NoSuchMethodException {

  }

  @Override
  public void visit(ObjectField value) {

  }

  @Override
  public void visit(StringField value) {

  }
}
