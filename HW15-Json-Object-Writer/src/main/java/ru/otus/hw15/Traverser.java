package ru.otus.hw15;

import ru.otus.hw15.misc.Checker;
import ru.otus.hw15.types.*;
import ru.otus.hw15.visitor.Visitor;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class Traverser {

  public static JsonObject traverse(Field mainField, Object object, Visitor service) throws IllegalAccessException, ClassNotFoundException, NoSuchMethodException {

    var jsonObj = Json.createObjectBuilder();

    // Обрабатываем сам объект и его поле, если указали
    if (object.getClass().isArray()) {
      jsonObj.add(mainField.getName(), new ArrayFiled(mainField, object).accept(service));
    }

    // TODO: примитивные типы и коллекции? потом только разбираем?
//    else {
//      new TraversedObject(mainField).accept(service);
//    }
    // Обрабатываем поля объекта
    Field[] fields = object.getClass().getDeclaredFields();
    for (Field field : fields) {
      field.setAccessible(true);
      if (Modifier.isStatic(field.getModifiers())) {
        continue;
      }
      jsonObj.add(field.getName(), Traverser.fieldAnalyzer(field, object, service));
    }
    return jsonObj.build();
  }
  // TODO: object
  public static JsonObject fieldAnalyzer(Field field, Object object, Visitor service) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException {

    var jsonObj = Json.createObjectBuilder();

    if (field.getType().isPrimitive()) {
      jsonObj.add(field.getName(), new PrimitiveField(field, field.get(object)).accept(service));
      // TODO: а можно сделать isAssignableFrom(Collection) и внутри пройтись итератором
      // https://docs.oracle.com/javase/8/docs/api/java/util/Collection.html
//      } else if (field.getType().isAssignableFrom(Set.class)) {
//        new SetFiled(field, (Set) field.get(object)).accept(service);
//      }
    } else if (Checker.isPrimitiveWrapper(object)) {
      jsonObj.add(field.getName(), new PrimitiveField(null, object).accept(service));
    } else if (Collection.class.isAssignableFrom(field.getType())) {
      jsonObj.add(field.getName(), new CollectionFiled(field, (Set) field.get(object)).accept(service));
    } else if (Map.class.isAssignableFrom(field.getType())) {
      jsonObj.add(field.getName(), new MapFiled(field, (Map) field.get(object)).accept(service));
    } else if (String.class.isAssignableFrom(field.getType())) {
      jsonObj.add(field.getName(), new StringField(field, field.get(object)).accept(service));
    } else if (field.getType().isArray()) {
      jsonObj.add(field.getName(), new ArrayFiled(field, field.get(object)).accept(service));
    } else {
      jsonObj.add(field.getName(), traverse(field, field.get(object), service));
    }

    return jsonObj.build();
  }

  public static JsonObject objectAnalyzer(Object object, Visitor service) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException {
    // TODO: не должен сюда попадать для примитивов?
    if (object instanceof Collection) {
      return new CollectionFiled(null, (Set) object).accept(service);
    } else if (object instanceof Map) {
      return new MapFiled(null, (Map) object).accept(service);
    } else if (object.getClass().isAssignableFrom(String.class)) {
      return new StringField(null, object).accept(service);
    } else if (object.getClass().isArray()) {
      return new ArrayFiled(null, object).accept(service);
    } else {
      return fieldAnalyzer(null, object, service);
    }
  }
}
