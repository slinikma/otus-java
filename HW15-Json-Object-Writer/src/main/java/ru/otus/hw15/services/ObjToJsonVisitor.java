package ru.otus.hw15.services;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import ru.otus.hw15.types.TraversedArray;
import ru.otus.hw15.types.TraversedObject;
import ru.otus.hw15.types.TraversedPrimitive;
import ru.otus.hw15.types.TraversedString;
import ru.otus.hw15.visitor.Visitor;

import java.lang.reflect.Array;
import java.util.ArrayList;

import ru.otus.hw15.misc.*;

public class ObjToJsonVisitor implements Visitor {
  private JsonObjectBuilder jsonCreated;

  public ObjToJsonVisitor() {
    jsonCreated = Json.createObjectBuilder();
  }

  @Override
  public void visit(TraversedArray value) throws ClassNotFoundException, NoSuchMethodException {
//    StringBuilder jsonArr = new StringBuilder();
    var jsonArr = Json.createArrayBuilder();
    Boolean isFirst = true;

    Class ofArray = value.getField().getType();
    if (ofArray.isPrimitive()) {
      ArrayList ar = new ArrayList();
    }
    Object array = value.getArray();
    int arrayLength = Array.getLength(value.getArray());
    for (var i = 0; i < arrayLength; i++) {
      Object arrElem = Array.get(array, i);
      if (arrElem != null) {
        System.out.println("Class: " + arrElem.getClass());
        System.out.println("IsPrimitive: " + arrElem.getClass().isPrimitive());
        if (arrElem.getClass().isPrimitive()) {
          new TraversedPrimitive(null, arrElem).accept(this);
        } else if (arrElem.getClass().isArray()) {
          new TraversedArray(null, arrElem).accept(this);
        } else if (Checker.isPrimitiveWrapper(arrElem)) {
          jsonArr.add((arrElem.getClass().getName()) arrElem);
        }
      }
    }
    System.out.println(jsonCreated.add(value.getName(), jsonArr));
  }

  @Override
  public void visit(TraversedPrimitive value) {
    // TODO: Как-то криво-сложно
    if (Boolean.class.equals(value.get().getClass())) {
      jsonCreated.add(value.getName(), (Boolean) value.get());
    } else if (Character.class.equals(value.get().getClass())) {
      jsonCreated.add(value.getName(), (Character) value.get());
    } else if (Byte.class.equals(value.get().getClass())) {
      jsonCreated.add(value.getName(), (Byte) value.get());
    } else if (Short.class.equals(value.get().getClass())) {
      jsonCreated.add(value.getName(), (Short) value.get());
    } else if (Integer.class.equals(value.get().getClass())) {
      jsonCreated.add(value.getName(), (Integer) value.get());
    } else if (Long.class.equals(value.get().getClass())) {
      jsonCreated.add(value.getName(), (Long) value.get());
    } else if (Float.class.equals(value.get().getClass())) {
      jsonCreated.add(value.getName(), (Float) value.get());
    } else if (Double.class.equals(value.get().getClass())) {
      jsonCreated.add(value.getName(), (Double) value.get());
    } else if (String.class.equals(value.get().getClass())) {
      jsonCreated.add(value.getName(), (String) value.get());
    } else {
      throw new IllegalArgumentException(":(");
    }
//
//     (JsonValue) value.get().getClass().cast(value.get()));
  }

  @Override
  public void visit(TraversedObject value) {
    System.out.println("Not implemented!");
  }

  @Override
  public void visit(TraversedString value) {
    jsonCreated.add(value.getName(), value.get());
  }

  public void printJson() {
    System.out.println(jsonCreated.build());
  }

}
