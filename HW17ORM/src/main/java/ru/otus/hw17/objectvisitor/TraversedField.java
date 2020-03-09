package ru.otus.hw17.objectvisitor;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public abstract class TraversedField implements TraversedType {
  @Getter private final Field field;
  @Getter private final Constructor classConstructor;
  @Getter private final String className;

  public TraversedField(Class clazz, Field field) throws NoSuchMethodException {
    this.field = field;
    this.classConstructor = clazz.getConstructor();
    this.className = clazz.getSimpleName();
  }

  public String getName() {
    return field == null ? "null" : field.getName();
  }

  public Annotation[] getAnnotations() {
    return field.getAnnotations();
  }

  public boolean isAnnotationPresent(Class<? extends Annotation> annotation) {
    return field.isAnnotationPresent(annotation);
  }

  abstract public TraversedField setObject(Object obj) throws IllegalAccessException;
}
