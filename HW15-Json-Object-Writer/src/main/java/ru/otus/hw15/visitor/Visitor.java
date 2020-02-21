package ru.otus.hw15.visitor;

import ru.otus.hw15.types.*;

import javax.json.JsonObject;


public interface Visitor {
  JsonObject visit(ArrayFiled value) throws ClassNotFoundException, NoSuchMethodException;
//  void visit(SetFiled value) throws ClassNotFoundException, NoSuchMethodException;
  JsonObject visit(CollectionFiled value) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException;
  JsonObject visit(MapFiled value) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException;
  JsonObject visit(PrimitiveField value) throws NoSuchMethodException;
  JsonObject visit(ObjectFiled value);
  JsonObject visit(StringField value);
  Object visit(Object value);
}
