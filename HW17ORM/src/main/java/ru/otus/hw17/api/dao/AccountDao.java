package ru.otus.hw17.api.dao;

import ru.otus.hw17.api.model.myorm.Account;
import ru.otus.hw17.api.sessionmanager.SessionManager;

import java.util.Optional;

public interface AccountDao {
  Optional<Account> getAccount(long id);
  long saveAccount(Account account);
  void updateAccount(Account account);

  SessionManager getSessionManager();
}
