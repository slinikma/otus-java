package ru.otus.hw17.api.dao;

public class AccountDaoException extends RuntimeException {
  public AccountDaoException(Exception ex) {
    super(ex);
  }
}
