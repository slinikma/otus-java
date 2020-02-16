package ru.otus.hw15.services;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

import ru.otus.hw15.types.ArrayField;
import ru.otus.hw15.types.ObjectField;
import ru.otus.hw15.types.PrimitiveField;
import ru.otus.hw15.types.StringField;
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
  public void visit(ArrayField value) throws ClassNotFoundException, NoSuchMethodException {
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
          new PrimitiveField(null, arrElem).accept(this);
        } else if (arrElem.getClass().isArray()) {
          new ArrayField(null, arrElem).accept(this);
        } else if (Checker.isPrimitiveWrapper(arrElem)) {
          jsonArr.add((arrElem.getClass().getName()));
        }
      }
    }
    System.out.println(jsonCreated.add(value.getName(), jsonArr));
  }

  @Override
  public void visit(PrimitiveField value) {
    // TODO: Как-то криво-сложно
    if (Boolean.class.equals(value.getBoxedPrimitive().getClass())) {
      jsonCreated.add(value.getName(), (Boolean) value.getBoxedPrimitive());
    } else if (Character.class.equals(value.getBoxedPrimitive().getClass())) {
      jsonCreated.add(value.getName(), (Character) value.getBoxedPrimitive());
    } else if (Byte.class.equals(value.getBoxedPrimitive().getClass())) {
      jsonCreated.add(value.getName(), (Byte) value.getBoxedPrimitive());
    } else if (Short.class.equals(value.getBoxedPrimitive().getClass())) {
      jsonCreated.add(value.getName(), (Short) value.getBoxedPrimitive());
    } else if (Integer.class.equals(value.getBoxedPrimitive().getClass())) {
      jsonCreated.add(value.getName(), (Integer) value.getBoxedPrimitive());
    } else if (Long.class.equals(value.getBoxedPrimitive().getClass())) {
      jsonCreated.add(value.getName(), (Long) value.getBoxedPrimitive());
    } else if (Float.class.equals(value.getBoxedPrimitive().getClass())) {
      jsonCreated.add(value.getName(), (Float) value.getBoxedPrimitive());
    } else if (Double.class.equals(value.getBoxedPrimitive().getClass())) {
      jsonCreated.add(value.getName(), (Double) value.getBoxedPrimitive());
    } else if (String.class.equals(value.getBoxedPrimitive().getClass())) {
      jsonCreated.add(value.getName(), (String) value.getBoxedPrimitive());
    } else {
      throw new IllegalArgumentException(":(");
    }
//
//     (JsonValue) value.get().getClass().cast(value.get()));
  }

  @Override
  public void visit(ObjectField value) {
    System.out.println("Not implemented!");
  }

  @Override
  public void visit(StringField value) {
    jsonCreated.add(value.getName(), value.getValue());
  }

  public void printJson() {
    System.out.println(jsonCreated.build());
  }

}
