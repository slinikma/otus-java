package ru.otus.hw15;

import lombok.AllArgsConstructor;
import ru.otus.hw15.services.ObjToJsonVisitor;
import ru.otus.hw15.types.*;
import ru.otus.hw15.visitor.Visitor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
public class MyGson {

  public String toJson(Object object) throws IllegalAccessException, ClassNotFoundException, NoSuchMethodException {
    var jsonSerializeService = new ObjToJsonVisitor();
    return Traverser.traverse(null, object, jsonSerializeService).toString();
  }
}
