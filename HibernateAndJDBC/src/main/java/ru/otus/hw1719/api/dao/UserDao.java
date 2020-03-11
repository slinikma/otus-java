package ru.otus.hw1719.api.dao;

import ru.otus.hw1719.api.model.User;
import ru.otus.hw1719.api.sessionmanager.SessionManager;

import java.util.Optional;

public interface UserDao {
  Optional<ru.otus.hw1719.api.model.User> getUser(long id);
  long saveUser(User user);
  void updateUser(User user);

  SessionManager getSessionManager();
}
