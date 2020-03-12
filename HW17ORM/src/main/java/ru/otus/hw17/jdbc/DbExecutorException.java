package ru.otus.hw17.jdbc;

public class DbExecutorException extends Exception {
  public DbExecutorException(String msg) {
    super(msg);
  }
  public DbExecutorException(Exception ex) {
    super(ex);
  }
}
