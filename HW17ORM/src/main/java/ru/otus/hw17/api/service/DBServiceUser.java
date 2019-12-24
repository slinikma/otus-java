package ru.otus.hw17.api.service;

import ru.otus.hw17.api.model.User;

import java.util.Optional;

public interface DBServiceUser {

  void saveUser(User user);

  Optional<User> getUser(long id);

}
