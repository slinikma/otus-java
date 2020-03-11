package ru.otus.hw1719.objectvisitor.misc;

public class Checker {
  public static boolean isPrimitiveWrapper(Object obj) {
    if (obj == null) {
      return false;
    }
    Class clazz = obj.getClass();
    // От проверки на Void.class смысла нет, вроде
    if (clazz == Boolean.class ||
        clazz == Character.class ||
        clazz == Byte.class ||
        clazz == Short.class ||
        clazz == Integer.class ||
        clazz == Long.class ||
        clazz == Float.class ||
        clazz == Double.class ||
        clazz == String.class)
    {
      return true;
    }
    return false;
  }
}
