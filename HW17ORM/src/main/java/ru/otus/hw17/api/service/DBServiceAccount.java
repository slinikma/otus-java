package ru.otus.hw17.api.service;

import ru.otus.hw17.api.model.Account;

import java.util.Optional;

public interface DBServiceAccount {
  void saveAccount(Account user);

  Optional<Account> getAccount(long id);
}
