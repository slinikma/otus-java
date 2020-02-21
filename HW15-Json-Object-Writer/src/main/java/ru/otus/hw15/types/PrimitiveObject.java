package ru.otus.hw15.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.otus.hw15.visitor.TraversedField;
import ru.otus.hw15.visitor.Visitor;

import javax.json.JsonObject;
import java.lang.reflect.Field;

@AllArgsConstructor
@Getter
public class PrimitiveObject {
  private final Object object;

  public Object accept(Visitor visitor) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException {
    return visitor.visit(this);
  }
}
