package ru.otus.hw09;

import ru.otus.hw09.annotations.Log;

public class MyClassImpl implements MyClassInterface {
  @Log
  public void calculation(int param) {};
}
