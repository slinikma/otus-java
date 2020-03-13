package ru.otus.hw09;

public class MagicLogging {

  public static void main(String[] args) {
    MyClassInterface myClass = IoC.createMyClass();
    myClass.calculation1(12);
    myClass.calculation2(24);
    myClass.calculation3(36);
  }
}
