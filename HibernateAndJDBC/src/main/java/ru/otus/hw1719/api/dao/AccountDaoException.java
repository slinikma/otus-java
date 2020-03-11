package ru.otus.hw1719.api.dao;

public class AccountDaoException extends RuntimeException {
  public AccountDaoException(Exception ex) {
    super(ex);
  }
}
