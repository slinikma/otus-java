package ru.otus.user;

import java.util.List;
import java.util.Optional;

public class UserService {

  private final UserDao userDao;

  public UserService(UserDao userDao) {
    this.userDao = userDao;
  }

  public boolean authenticate(String login, String password) {
    return Optional.ofNullable(userDao.findByLogin(login))
        .map(user -> user.getPassword().equals(password))
        .orElse(false);
  }

  public void createUser(String login, String password) {
    userDao.save(new User(login, password));
  }

  public List<User> getAllUsers() {
    return userDao.getAllUsers();
  }
}
