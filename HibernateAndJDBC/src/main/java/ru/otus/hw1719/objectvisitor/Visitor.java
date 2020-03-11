package ru.otus.hw1719.objectvisitor;

import ru.otus.hw1719.objectvisitor.visitable.types.ArrayField;
import ru.otus.hw1719.objectvisitor.visitable.types.ObjectField;
import ru.otus.hw1719.objectvisitor.visitable.types.PrimitiveField;
import ru.otus.hw1719.objectvisitor.visitable.types.StringField;

public interface Visitor {
  void visit(ArrayField field) throws ClassNotFoundException, NoSuchMethodException;
  void visit(PrimitiveField field) throws NoSuchMethodException;
  void visit(ObjectField field) throws NoSuchMethodException;
  void visit(StringField field) throws NoSuchMethodException;
}
