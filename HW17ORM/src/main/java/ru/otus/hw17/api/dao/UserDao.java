package ru.otus.hw17.api.dao;

import java.util.Optional;

import ru.otus.hw17.api.model.User;
import ru.otus.hw17.api.sessionmanager.SessionManager;

public interface UserDao {
  Optional<User> getUser(long id);
  void saveUser(User user);

  SessionManager getSessionManager();
}
