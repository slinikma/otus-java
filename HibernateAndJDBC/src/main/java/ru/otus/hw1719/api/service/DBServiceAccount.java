package ru.otus.hw1719.api.service;

import ru.otus.hw1719.api.model.myorm.Account;

import java.util.Optional;

public interface DBServiceAccount {
  long saveAccount(Account account);
  void updateAccount(Account account);
  Optional<Account> getAccount(long id);
}
