package ru.otus.hw07;

public class ATMException extends Exception {
  public ATMException(String msg) {
    super(msg);
  }

  public ATMException(Exception ex) {
    super(ex);
  }
}
