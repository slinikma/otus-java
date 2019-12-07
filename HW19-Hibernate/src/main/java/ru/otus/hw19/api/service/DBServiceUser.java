package ru.otus.hw19.api.service;

import ru.otus.hw19.api.model.User;

import java.util.Optional;

public interface DBServiceUser {
  void update(User user);
  Optional<User> getUser(long id);
}
