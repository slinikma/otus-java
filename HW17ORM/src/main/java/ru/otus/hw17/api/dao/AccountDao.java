package ru.otus.hw17.api.dao;

import ru.otus.hw17.api.model.Account;
import ru.otus.hw17.api.sessionmanager.SessionManager;

import java.util.Optional;

public interface AccountDao {
  Optional<Account> getAccount(long id);
  void saveAccount(Account account);

  SessionManager getSessionManager();
}
