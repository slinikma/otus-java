package ru.otus.hw1719.api.service;


import ru.otus.hw1719.api.model.User;

import java.util.Optional;

public interface DBServiceUser {
  long saveUser(User user);
  void updateUser(User user);
  Optional<User> getUser(long id);
}
