package ru.otus.hw15;

import lombok.AllArgsConstructor;
import ru.otus.hw15.services.ObjToJsonVisitor;
import ru.otus.hw15.types.ArrayField;
import ru.otus.hw15.types.ObjectField;
import ru.otus.hw15.types.PrimitiveField;
import ru.otus.hw15.types.StringField;
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
      new ArrayField(mainField, object).accept(service);
    } else {
      new ObjectField(mainField).accept(service);
    }
    // Обрабатываем поля объекта
    Field[] fields = object.getClass().getDeclaredFields();
    for (Field field : fields) {
      field.setAccessible(true);
      if (Modifier.isStatic(field.getModifiers())) {
        continue;
      }
      if (field.getType().isPrimitive()) {
        new PrimitiveField(field, field.get(object)).accept(service);
      } else if (field.getType().isArray()) {
        new ArrayField(field, field.get(object)).accept(service);
      } else if (field.getType().isAssignableFrom(String.class)) {
        new StringField(field, field.get(object)).accept(service);
      } else {
        traverse(field, field.get(object), service);
      }
    }
  }
}
