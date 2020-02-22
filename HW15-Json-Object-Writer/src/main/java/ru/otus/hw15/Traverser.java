package ru.otus.hw15;

import ru.otus.hw15.misc.Checker;
import ru.otus.hw15.types.*;
import ru.otus.hw15.visitor.Visitor;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;

public class Traverser {

  public static JsonObject traverse(Field mainField, Object object, Visitor service) throws IllegalAccessException, ClassNotFoundException, NoSuchMethodException {

    var jsonObj = Json.createObjectBuilder();

    // Обрабатываем сам объект и его поле, если указали
    if (object.getClass().isArray()) {
      jsonObj.add(mainField.getName(), new ArrayFiled(mainField, object).accept(service));
    }

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
  public static JsonValue fieldAnalyzer(Field field, Object object, Visitor service) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException {


    if (field.getType().isPrimitive()) {
      return new PrimitiveField(field, field.get(object)).accept(service);
      // TODO: а можно сделать isAssignableFrom(Collection) и внутри пройтись итератором
      // https://docs.oracle.com/javase/8/docs/api/java/util/Collection.html
//      } else if (field.getType().isAssignableFrom(Set.class)) {
//        new SetFiled(field, (Set) field.get(object)).accept(service);
//      }
    } else if (Checker.isPrimitiveWrapper(object)) {
      return new PrimitiveField(null, object).accept(service);
    } else if (Collection.class.isAssignableFrom(field.getType())) {
      return new CollectionFiled(field, (Collection) field.get(object)).accept(service);
    } else if (Map.class.isAssignableFrom(field.getType())) {
      return new MapFiled(field, (Map) field.get(object)).accept(service);
    } else if (String.class.isAssignableFrom(field.getType())) {
      return new StringField(field, field.get(object)).accept(service);
    }
    else if (field.getType().isArray()) {
      return new ArrayFiled(field, field.get(object)).accept(service);
    } else {
      return traverse(field, field.get(object), service);
    }

  }

  public static JsonValue objectAnalyzer(Object object, Visitor service) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException {
    // TODO: не должен сюда попадать для примитивов?
    if (Collection.class.isAssignableFrom(object.getClass())) {
      return new CollectionFiled(null, (Collection) object).accept(service);
    } else if (Map.class.isAssignableFrom(object.getClass())) {
      return new MapFiled(null, (Map) object).accept(service);
    } else if (object.getClass().isArray()) {
      return new ArrayFiled(null, object).accept(service);
    } else {
      return fieldAnalyzer(null, object, service);
    }
  }
}
