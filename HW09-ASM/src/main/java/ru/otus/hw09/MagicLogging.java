package ru.otus.hw09;

public class MagicLogging {

  public static void main(String[] args) {
    MyClassInterface myClass = IoC.createMyClass();
    myClass.calculation(12);
  }
}
