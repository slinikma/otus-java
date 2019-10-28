package ru.otus.hw06;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.otus.hw06.myjunit.MyJunitCore;

@ExtendWith(MyJunitCore.class)
public class CompareWithJunitTest {

  Integer junitIntVar;
  Integer myJunitIntVar;

  @BeforeAll
  public static void junit5BeforeAll() {
    System.out.println("Junit 5 @BeforeAll");
  }

  @ru.otus.hw06.annotations.BeforeAll
  public static void myJunitBeforeAll() {
    System.out.println("My Junit @BeforeAll");
  }


  @AfterAll
  public static void junit5AfterAll() {
    System.out.println("Junit 5 @AfterAll");
  }

  @ru.otus.hw06.annotations.AfterAll
  public static void myJunitAfterAll() {
    System.out.println("My Junit @AfterAll");
  }


  @BeforeEach
  public void junit5SetVars() {

  }

  @ru.otus.hw06.annotations.BeforeEach
  public void myJunitSetVars() {

  }


  @Test
  public void junit5MakeTest() {

  }

  @ru.otus.hw06.annotations.Test
  public void myJunitMakeTest() {

  }


  @AfterEach
  public void junit5AfterTest() {

  }

  @ru.otus.hw06.annotations.AfterEach
  public void myJunitAfterTest() {

  }
}
