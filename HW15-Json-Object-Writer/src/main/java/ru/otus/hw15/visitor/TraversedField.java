package ru.otus.hw15.visitor;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Field;

@AllArgsConstructor
@Getter
public abstract class TraversedField implements TraversedType {
  private final Field field;

  public String getName() {
    return field == null ? "null" : field.getName();
  }
}
