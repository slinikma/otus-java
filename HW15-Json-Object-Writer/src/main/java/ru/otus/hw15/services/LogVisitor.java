package ru.otus.hw15.services;

import ru.otus.hw15.types.TraversedArray;
import ru.otus.hw15.types.TraversedObject;
import ru.otus.hw15.types.TraversedPrimitive;
import ru.otus.hw15.types.TraversedString;
import ru.otus.hw15.visitor.Visitor;

public class LogVisitor implements Visitor {
  @Override
  public void visit(TraversedArray value) {
    System.out.println("Array: " + value.getName());
  }

  @Override
  public void visit(TraversedPrimitive value) {
    System.out.println("Primitive: " + value.getName());
  }

  @Override
  public void visit(TraversedObject value) {
    System.out.println("Object: " + value.getName());
  }

  @Override
  public void visit(TraversedString value) {
    System.out.println("String: " + value.getName());
  }
}
