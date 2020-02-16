package ru.otus.hw15.services;

import ru.otus.hw15.types.ArrayField;
import ru.otus.hw15.types.ObjectField;
import ru.otus.hw15.types.PrimitiveField;
import ru.otus.hw15.types.StringField;
import ru.otus.hw15.visitor.Visitor;

public class LogVisitor implements Visitor {
  @Override
  public void visit(ArrayField value) {
    System.out.println("Array: " + value.getName());
  }

  @Override
  public void visit(PrimitiveField value) {
    System.out.println("Primitive: " + value.getName());
  }

  @Override
  public void visit(ObjectField value) {
    System.out.println("Object: " + value.getName());
  }

  @Override
  public void visit(StringField value) {
    System.out.println("String: " + value.getName());
  }
}
