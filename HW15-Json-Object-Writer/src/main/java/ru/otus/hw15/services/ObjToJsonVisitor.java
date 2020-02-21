package ru.otus.hw15.services;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import ru.otus.hw15.Traverser;
import ru.otus.hw15.types.*;
import ru.otus.hw15.visitor.Visitor;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import ru.otus.hw15.misc.*;

public class ObjToJsonVisitor implements Visitor {
  private JsonObjectBuilder jsonCreated;

  public ObjToJsonVisitor() {
    jsonCreated = Json.createObjectBuilder();
  }

  @Override
  public JsonObject visit(ArrayFiled value) throws ClassNotFoundException, NoSuchMethodException {
//    StringBuilder jsonArr = new StringBuilder();
//    Boolean isFirst = true;
    var jsonArr = Json.createArrayBuilder();
    JsonObject jsonObj = null;

    Class ofArray = value.getField().getType();
//    if (ofArray.isPrimitive()) {
//      ArrayList ar = new ArrayList();
//    }
    Object array = value.getArray();
    int arrayLength = Array.getLength(value.getArray());
    for (var i = 0; i < arrayLength; i++) {
      Object arrElem = Array.get(array, i);
      if (arrElem != null) {
        if (arrElem.getClass().isPrimitive()) {
          new PrimitiveField(null, arrElem).accept(this);
        } else if (arrElem.getClass().isArray()) {
          new ArrayFiled(null, arrElem).accept(this);
        } else if (Checker.isPrimitiveWrapper(arrElem)) {
          jsonArr.add(arrElem.toString());
        }
      }
    }
//    System.out.println(jsonCreated.add(value.getName(), jsonArr));
    return null;
  }

//  @Override
//  public void visit(SetFiled value) throws ClassNotFoundException, NoSuchMethodException {
//    var jsonArr = Json.createArrayBuilder();
//
//    Set set = value.getSet();
//    // TODO: для String и Set, List, Map, Queue
//    for (var setElem : set) {
//      if (setElem != null) {
//        System.out.println("Class: " + setElem.getClass());
//        System.out.println("IsPrimitive: " + setElem.getClass().isPrimitive());
//        if (setElem.getClass().isPrimitive()) {
//          new PrimitiveField(null, setElem).accept(this);
//        } else if (setElem.getClass().isArray()) {
//          new ArrayFiled(null, setElem).accept(this);
//        } else if (Checker.isPrimitiveWrapper(setElem)) {
//          jsonArr.add(setElem.toString());
//        }
//      }
//    }
//    System.out.println(jsonCreated.add(value.getName(), jsonArr));
//  }

  @Override
  public JsonObject visit(CollectionFiled value) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException {
    var jsonArr = Json.createArrayBuilder();
    var jsonObj = Json.createObjectBuilder();

    Collection collection = value.getCollection();
    // TODO: для String и Set, List, Map, Queue
    for (var collectionElem : collection) {
      if (collectionElem != null) {
        if (collectionElem.getClass().isPrimitive()) {
          jsonArr.add(new PrimitiveObject(collectionElem).accept(this));
        } else if (Checker.isPrimitiveWrapper(collectionElem)) {
          jsonArr.add(collectionElem.toString());
//          jsonArr.add(Json.createValue(mapElem.getValue().toString()));
        } else {
          jsonArr.add(Traverser.objectAnalyzer(collectionElem, this));
        }
//        if (collectionElem.getClass().isPrimitive()) {
//          jsonArr.add(new PrimitiveField(null, collectionElem).accept(this));
//        } else if (collectionElem.getClass().isArray()) {
//          jsonArr.add(new ArrayFiled(null, collectionElem).accept(this));
//        } else if (Checker.isPrimitiveWrapper(collectionElem)) {
//          jsonArr.add(collectionElem.toString());
//        }
      }
    }
//    System.out.println(jsonCreated.add(value.getName(), jsonArr));
    return jsonCreated.add(value.getName(), jsonArr).build();
  }

  @Override
  public JsonObject visit(MapFiled value) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException {
//    var jsonArr = Json.createObjectBuilder();
    var jsonObj = Json.createObjectBuilder();
    String jKey = "";
    JsonValue jValue = null;
//    var mapElemStr = new StringBuilder();

    Map<Object, Object> map = value.getMap();
    // TODO: для String и Set, List, Map, Queue
    for (Map.Entry<Object, Object> mapElem : map.entrySet()) {
      if (mapElem != null) {
//        System.out.println("Key class: " + mapElem.getKey().getClass());
//        System.out.println("Key IsPrimitive: " + mapElem.getKey().getClass().isPrimitive());
//
//        System.out.println("Value class: " + mapElem.getValue().getClass());
//        System.out.println("Value IsPrimitive: " + mapElem.getValue().getClass().isPrimitive());
        if (mapElem.getKey().getClass().isPrimitive()) {
          new PrimitiveField(null, mapElem.getKey()).accept(this);
        } else if (mapElem.getKey().getClass().isArray()) {
          new ArrayFiled(null, mapElem.getKey()).accept(this);
        } else if (Checker.isPrimitiveWrapper(mapElem.getKey())) {
          jKey = mapElem.getKey().toString();
        }

        if (mapElem.getValue().getClass().isPrimitive()) {
          jValue = Json.createValue(mapElem.getValue().toString());
        } else if (Checker.isPrimitiveWrapper(mapElem.getValue())) {
          jValue = Json.createValue(mapElem.getValue().toString());
        } else {
          jValue = Traverser.objectAnalyzer(mapElem.getValue(), this);
        }
//        if (mapElem.getValue().getClass().isPrimitive()) {
//          new PrimitiveField(null, mapElem.getValue()).accept(this);
//        } else if (mapElem.getValue().getClass().isArray()) {
//          new ArrayFiled(null, mapElem.getValue()).accept(this);
//        } else if (Checker.isPrimitiveWrapper(mapElem.getValue())) {
//          jValue = Json.createValue(mapElem.getValue().toString());
//        } else {
////          Traverser.traverse(null, mapElem.getValue(), this);
//          jValue = Traverser.objectAnalyzer(mapElem.getValue(), this);
//        }
      }

      jsonObj.add(jKey, jValue);
    }

    return jsonObj.build();
//    System.out.println(jsonCreated.add(value.getName(), jsonArr));
    // TODO: Optional?
//    if (value.getName() == "null") {
//      return jsonArr.build();
//    } else {
//      return jsonObj.add(value.getName(), jsonArr).build();
//    }
  }

  @Override
  public JsonObject visit(PrimitiveField value) {

    var jsonObj = Json.createObjectBuilder();

    // TODO: Как-то криво-сложно
    if (Boolean.class.equals(value.get().getClass())) {
      jsonObj.add(value.getName(), (Boolean) value.get());
    } else if (Character.class.equals(value.get().getClass())) {
      jsonObj.add(value.getName(), (Character) value.get());
    } else if (Byte.class.equals(value.get().getClass())) {
      jsonObj.add(value.getName(), (Byte) value.get());
    } else if (Short.class.equals(value.get().getClass())) {
      jsonObj.add(value.getName(), (Short) value.get());
    } else if (Integer.class.equals(value.get().getClass())) {
      jsonObj.add(value.getName(), (Integer) value.get());
    } else if (Long.class.equals(value.get().getClass())) {
      jsonObj.add(value.getName(), (Long) value.get());
    } else if (Float.class.equals(value.get().getClass())) {
      jsonObj.add(value.getName(), (Float) value.get());
    } else if (Double.class.equals(value.get().getClass())) {
      jsonObj.add(value.getName(), (Double) value.get());
    } else if (String.class.equals(value.get().getClass())) {
      jsonObj.add(value.getName(), (String) value.get());
    } else {
      throw new IllegalArgumentException(":(");
    }
//
//     (JsonValue) value.get().getClass().cast(value.get()));
    return jsonObj.build();
  }

  @Override
  public JsonObject visit(ObjectFiled value) {
    System.out.println("Not implemented!");
    return null;
  }

  @Override
  public JsonObject visit(StringField value) {
    jsonCreated.add(value.getName(), value.get());
    return null;
  }

  public void printJson() {
    System.out.println(jsonCreated.build());
  }

}
