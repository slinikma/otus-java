package ru.otus.hw15.services;

public class Test {
  public static void main(String[] args) {
    Integer test = 2;
    System.out.println("Class: " + test.getClass());
    System.out.println("is? " + test.getClass().isPrimitive());
    System.out.println(Integer.class.isPrimitive());
  }
}
