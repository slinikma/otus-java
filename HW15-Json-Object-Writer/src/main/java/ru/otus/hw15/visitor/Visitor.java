package ru.otus.hw15.visitor;

import ru.otus.hw15.types.TraversedArray;
import ru.otus.hw15.types.TraversedObject;
import ru.otus.hw15.types.TraversedPrimitive;
import ru.otus.hw15.types.TraversedString;

public interface Visitor {
  void visit(TraversedArray value) throws ClassNotFoundException, NoSuchMethodException;
  void visit(TraversedPrimitive value) throws NoSuchMethodException;
  void visit(TraversedObject value);
  void visit(TraversedString value);
}
