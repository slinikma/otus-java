package ru.otus.user;

import java.util.List;

public interface UserDao {

  void save(User user);

  User findByLogin(String name);

  List<User> getAllUsers();
}
