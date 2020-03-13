package ru.otus.hw09;

import ru.otus.hw09.annotations.Log;

public class MyClassImpl implements MyClassInterface {
  @Log
  public void calculation1(int param) {};
  public void calculation2(int param) {};
  @Log
  public void calculation3(int param) {};
}
