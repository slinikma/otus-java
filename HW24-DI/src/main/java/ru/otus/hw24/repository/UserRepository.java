package ru.otus.hw24.repository;

import ru.otus.hw24.domain.User;

import java.util.List;

// Заменили UserDao -> UserRepository
public interface UserRepository {
  void save(String login, String password);

  User findByLogin(String name);

  List<User> getAllUsers();
}
