package ru.otus.hw06.myjunit;

import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import ru.otus.hw06.annotations.AfterEach;
import ru.otus.hw06.annotations.BeforeEach;
import ru.otus.hw06.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import org.reflections.Reflections;

public class MyJunitCore {

  private static Object cls_instance;

  private static LinkedHashSet<Class<?>> cls_set = new LinkedHashSet<>();

  public static void runClass(Class<?> cls) {

    List<Method> beforeMethods = new ArrayList<Method>();
    List<Method> testMethods = new ArrayList<Method>();
    List<Method> afterMethods = new ArrayList<Method>();

    try {
      cls_instance = cls.getDeclaredConstructor().newInstance();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }


    for (final Method mtd : cls.getDeclaredMethods()) {

      if (mtd.isAnnotationPresent(BeforeEach.class)) {
        mtd.setAccessible(true);
        beforeMethods.add(mtd);
      }

      if (mtd.isAnnotationPresent(Test.class)) {
        mtd.setAccessible(true);
        testMethods.add(mtd);
      }

      if (mtd.isAnnotationPresent(AfterEach.class)) {
        mtd.setAccessible(true);
        afterMethods.add(mtd);
      }
    }

    for (final Method mtd: beforeMethods) {
      try {
        Annotation annotInstance = mtd.getAnnotation(BeforeEach.class);
        mtd.invoke(cls_instance);
      } catch (InvocationTargetException ite) {
        System.out.println("[My Junit] " + ite.getCause());
      } catch (Exception e) {
        new Exception("[MyJunit] @Before: " + e.getMessage());
      }
    }

    for (final Method mtd: testMethods) {
      try {
        Annotation annotInstance = mtd.getAnnotation(Test.class);
        mtd.invoke(cls_instance);
      } catch (InvocationTargetException ite) {
        System.out.println("[My Junit] " + ite.getCause());
      } catch (Exception e) {
        new Exception("[MyJunit] @Test: " + e.getMessage());
      }
    }

    for (final Method mtd: afterMethods) {
      try {
        Annotation annotInstance = mtd.getAnnotation(AfterEach.class);
        mtd.invoke(cls_instance);
      } catch (InvocationTargetException ite) {
        System.out.println("[My Junit] " + ite.getCause());
      } catch (Exception e) {
        new Exception("[MyJunit] @After: " + e.getMessage());
      }
    }
  }

  public static void runPackage(String pkg) {
    Reflections reflections = new Reflections(new ConfigurationBuilder()
        .setUrls(ClasspathHelper.forPackage(pkg))
        .setScanners(new MethodAnnotationsScanner(),
            new SubTypesScanner(),
            new TypeAnnotationsScanner()));

    // Form set of classes using reflections API
    Set<Method> testAnnotatedmethods =
        reflections.getMethodsAnnotatedWith(Test.class);
    for (final Method mtd : testAnnotatedmethods) {
      cls_set.add(mtd.getDeclaringClass());
    }

    for (Class<?> obj : cls_set) {
      runClass(obj);
    }
  }
}
