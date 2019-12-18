package ru.otus.hw17.api.service;

public class DbServiceException extends RuntimeException {
  public DbServiceException(Exception e) {
    super(e);
  }
}
