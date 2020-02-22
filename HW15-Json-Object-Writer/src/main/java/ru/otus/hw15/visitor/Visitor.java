package ru.otus.hw15.visitor;

import ru.otus.hw15.types.*;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;


public interface Visitor {
  JsonValue visit(ArrayFiled value) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException;
//  void visit(SetFiled value) throws ClassNotFoundException, NoSuchMethodException;
  JsonArray visit(CollectionFiled value) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException;
  JsonObject visit(MapFiled value) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException;
  JsonValue visit(PrimitiveField value) throws NoSuchMethodException;
  JsonValue visit(StringField value);
}
