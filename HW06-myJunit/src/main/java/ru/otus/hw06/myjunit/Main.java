package ru.otus.hw06.myjunit;

import ru.otus.hw06.tests.TestSuites1;

public class Main {

  public static void main(String[] args) {
    MyJunitCore.runClass(TestSuites1.class);
    System.out.println("=======PACKAGE=======");
    MyJunitCore.runPackage("tests");
  }
}
