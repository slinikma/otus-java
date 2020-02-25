package ru.otus.hw14.atm;

public class ATMException extends Exception {
  public ATMException(String msg) {
    super(msg);
  }

  public ATMException(Exception ex) {
    super(ex);
  }
}
