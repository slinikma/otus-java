package ru.otus.hw15;

import lombok.AllArgsConstructor;
import ru.otus.hw15.services.LogVisitor;
import ru.otus.hw15.services.ObjToJsonVisitor;
import ru.otus.hw15.types.TraversedArray;
import ru.otus.hw15.types.TraversedObject;
import ru.otus.hw15.types.TraversedPrimitive;
import ru.otus.hw15.types.TraversedString;
import ru.otus.hw15.visitor.Visitor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@AllArgsConstructor
public class MyGson {

  public String toJson(Object object) throws IllegalAccessException, ClassNotFoundException, NoSuchMethodException {
    var jsonSerializeService = new ObjToJsonVisitor();
    traverse(null, object, jsonSerializeService);

    jsonSerializeService.printJson();

    return "NaN";
  }

  private void traverse(Field mainField, Object object, Visitor service) throws IllegalAccessException, ClassNotFoundException, NoSuchMethodException {
    // Обрабатываем сам объект и его поле, если указали
    if (object.getClass().isArray()) {
      new TraversedArray(mainField, object).accept(service);
    } else {
      new TraversedObject(mainField).accept(service);
    }
    // Обрабатываем поля объекта
    Field[] fields = object.getClass().getDeclaredFields();
    for (Field field : fields) {
      field.setAccessible(true);
      if (Modifier.isStatic(field.getModifiers())) {
        continue;
      }
      if (field.getType().isPrimitive()) {
        new TraversedPrimitive(field, field.get(object)).accept(service);
      } else if (field.getType().isArray()) {
        new TraversedArray(field, field.get(object)).accept(service);
      } else if (field.getType().isAssignableFrom(String.class)) {
        new TraversedString(field, field.get(object)).accept(service);
      } else {
        traverse(field, field.get(object), service);
      }
    }
  }
}
