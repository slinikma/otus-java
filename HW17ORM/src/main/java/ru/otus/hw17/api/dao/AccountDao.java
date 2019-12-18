package ru.otus.hw17.api.dao;

import ru.otus.hw17.api.model.User;
import ru.otus.hw17.api.sessionmanager.SessionManager;

import java.util.Optional;

public interface AccountDao {
  Optional<User> findById(long id);
  long saveUser(User user);

  SessionManager getSessionManager();
}
