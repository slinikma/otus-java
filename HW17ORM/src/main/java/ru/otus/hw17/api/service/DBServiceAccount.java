package ru.otus.hw17.api.service;

import ru.otus.hw17.api.model.Account;

import java.util.Optional;

public interface DBServiceAccount {
  long saveAccount(Account account);

  void updateAccount(Account account);

  Optional<Account> getAccount(long id);
}
