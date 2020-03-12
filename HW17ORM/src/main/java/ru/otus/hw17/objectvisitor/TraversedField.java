package ru.otus.hw17.objectvisitor;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@AllArgsConstructor
public abstract class TraversedField implements TraversedType {
  @Getter
  private final Field field;

  public String getName() {
    return field == null ? "null" : field.getName();
  }

  public Annotation[] getAnnotations() {
    return field.getAnnotations();
  }

  public boolean isAnnotationPresent(Class<? extends Annotation> annotation) {
    return field.isAnnotationPresent(annotation);
  }
}
