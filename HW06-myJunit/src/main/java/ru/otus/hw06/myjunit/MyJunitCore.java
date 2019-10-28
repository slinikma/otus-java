package ru.otus.hw06.myjunit;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import ru.otus.hw06.annotations.BeforeAll;
import ru.otus.hw06.annotations.MyCustomAnnotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyJunitCore implements BeforeAllCallback {

  enum Annotations {
    BeforeAll,
    AfterAll,
    BeforeEach,
    AfterEach,
    Test
  }

  @Override
  public void beforeAll(ExtensionContext extensionContext) throws Exception {

    Class clazz = extensionContext.getRequiredTestClass();
    Stream<Method> methodStream = Arrays.stream(clazz.getDeclaredMethods());
    Object obj = clazz.getConstructor().newInstance();

    Map<Annotation, List<Method>> annotationMethodMap = methodStream.map(method ->
        Arrays.stream(method.getDeclaredAnnotations())
            .filter(annotation -> annotation.annotationType().isAnnotationPresent(MyCustomAnnotation.class))
            .collect(Collectors.toMap(p -> p. annotationType(), Arrays::asList)));
//            .collect(() -> {
//              Map<Annotations, List<Method>> map = new HashMap<>();
//              map.put(Annotations.BeforeAll, new ArrayList<>());
//              map.put(Annotations.AfterAll, new ArrayList<>());
//              map.put(Annotations.BeforeEach, new ArrayList<>());
//              map.put(Annotations.AfterEach, new ArrayList<>());
//              map.put(Annotations.Test, new ArrayList<>());
//              return map;
//            }, (map, annotation) -> {
//              annotation::annotationType();
//
//              List<Integer> list = map.get(partition);
//              list.add(x);
//            }, (map1, map2) -> {
//              map1.get(false).addAll(map2.get(false));
//              map1.get(true).addAll(map2.get(true));
//
//            })
    );

    System.out.println("Class annotations: " + clazz.getAnnotations().length);

    for (var method : clazz.getDeclaredMethods()) {
      System.out.println("Method name: " + method.getName());
    }

    clazz.getDeclaredMethods().

  }
}
