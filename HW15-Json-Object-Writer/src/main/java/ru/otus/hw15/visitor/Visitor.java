package ru.otus.hw15.visitor;

import ru.otus.hw15.types.ArrayField;
import ru.otus.hw15.types.ObjectField;
import ru.otus.hw15.types.PrimitiveField;
import ru.otus.hw15.types.StringField;

public interface Visitor {
  void visit(ArrayField value) throws ClassNotFoundException, NoSuchMethodException;
  void visit(PrimitiveField value) throws NoSuchMethodException;
  void visit(ObjectField value);
  void visit(StringField value);
}
