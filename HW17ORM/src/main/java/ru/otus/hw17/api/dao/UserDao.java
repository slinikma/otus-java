package ru.otus.hw17.api.dao;

import java.util.Optional;

import ru.otus.hw17.api.model.User;
import ru.otus.hw17.api.sessionmanager.SessionManager;

public interface UserDao {
  Optional<ru.otus.hw17.api.model.User> getUser(long id);

  long saveUser(User user);

  void updateUser(User user);

  SessionManager getSessionManager();
}
