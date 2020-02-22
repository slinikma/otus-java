package ru.otus.hw15.services;

import javax.json.*;

import ru.otus.hw15.Traverser;
import ru.otus.hw15.types.*;
import ru.otus.hw15.visitor.Visitor;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

import ru.otus.hw15.misc.*;

public class ObjToJsonVisitor implements Visitor {
  private JsonObjectBuilder jsonCreated;

  public ObjToJsonVisitor() {
    jsonCreated = Json.createObjectBuilder();
  }

  @Override
  public JsonValue visit(ArrayFiled value) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException {

    var jsonArr = Json.createArrayBuilder();

    Object array = value.getArray();
    int arrayLength = Array.getLength(value.getArray());
    for (var i = 0; i < arrayLength; i++) {
      Object arrElem = Array.get(array, i);
      if (arrElem != null) {
        if (arrElem.getClass().isPrimitive()) {
          jsonArr.add(primitiveObjectToJsonValue(arrElem));
        } else if (Checker.isPrimitiveWrapper(arrElem)) {
          jsonArr.add(primitiveObjectToJsonValue(arrElem));
        } else {
          jsonArr.add(Traverser.objectAnalyzer(arrElem, this));
        }
      }
    }

    return jsonArr.build();
  }

//  @Override
//  public void visit(SetFiled value) throws ClassNotFoundException, NoSuchMethodException {
//    var jsonArr = Json.createArrayBuilder();
//
//    Set set = value.getSet();
//
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
  public JsonArray visit(CollectionFiled value) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException {

    var jsonArr = Json.createArrayBuilder();

    Collection collection = value.getCollection();

    for (var collectionElem : collection) {
      if (collectionElem != null) {
        if (collectionElem.getClass().isPrimitive()) {
          jsonArr.add(primitiveObjectToJsonValue(collectionElem));
        } else if (Checker.isPrimitiveWrapper(collectionElem)) {
          jsonArr.add(primitiveObjectToJsonValue(collectionElem));
        } else {
          jsonArr.add(Traverser.objectAnalyzer(collectionElem, this));
        }
      }
    }
    return jsonArr.build();
  }

  @Override
  public JsonObject visit(MapFiled value) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException {

    var jsonObj = Json.createObjectBuilder();
    String jKey = "";
    JsonValue jValue = null;

    Map<Object, Object> map = value.getMap();
    for (Map.Entry<Object, Object> mapElem : map.entrySet()) {
      if (mapElem != null) {

        if (mapElem.getKey().getClass().isPrimitive()) {
          new PrimitiveField(null, mapElem.getKey()).accept(this);
        } else if (mapElem.getKey().getClass().isArray()) {
          new ArrayFiled(null, mapElem.getKey()).accept(this);
        } else if (Checker.isPrimitiveWrapper(mapElem.getKey())) {
          jKey = mapElem.getKey().toString();
        }

        if (mapElem.getValue().getClass().isPrimitive()) {
          jValue = primitiveObjectToJsonValue(mapElem.getValue());
        } else if (Checker.isPrimitiveWrapper(mapElem.getValue())) {
          jValue = primitiveObjectToJsonValue(mapElem.getValue());
        } else {
          jValue = Traverser.objectAnalyzer(mapElem.getValue(), this);
        }
      }

      jsonObj.add(jKey, jValue);
    }

    return jsonObj.build();
  }

  @Override
  public JsonValue visit(PrimitiveField value) {
    return primitiveObjectToJsonValue(value.get());
  }

  @Override
  public JsonObject visit(ObjectFiled value) {
    System.out.println("Not implemented!");
    return null;
  }

  @Override
  public JsonValue visit(StringField value) {
    return Json.createValue(value.get());
  }

  private JsonValue primitiveObjectToJsonValue(Object o) {

    JsonValue jsonValue;

    if (Boolean.class.equals(o.getClass())) {
      if ((Boolean) o) {
        jsonValue = JsonValue.TRUE;
      } else {
        jsonValue = JsonValue.FALSE;
      }
    } else if (Character.class.equals(o.getClass())) {
      jsonValue = Json.createValue((Character) o);
    } else if (Byte.class.equals(o.getClass())) {
      jsonValue = Json.createValue((Byte) o);
    } else if (Short.class.equals(o.getClass())) {
      jsonValue = Json.createValue((Short) o);
    } else if (Integer.class.equals(o.getClass())) {
      jsonValue = Json.createValue((Integer) o);
    } else if (Long.class.equals(o.getClass())) {
      jsonValue = Json.createValue((Long) o);
    } else if (Float.class.equals(o.getClass())) {
      jsonValue = Json.createValue((Float) o);
    } else if (Double.class.equals(o.getClass())) {
      jsonValue = Json.createValue((Double) o);
    } else if (String.class.equals(o.getClass())) {
      jsonValue = Json.createValue((String) o);
    } else {
      throw new IllegalArgumentException();
    }

    return jsonValue;
  }

  public void printJson() {
    System.out.println(jsonCreated.build());
  }

}
