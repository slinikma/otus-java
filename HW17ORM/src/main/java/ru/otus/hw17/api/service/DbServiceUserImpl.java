package ru.otus.hw17.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw17.api.sessionmanager.SessionManager;
import ru.otus.hw17.api.dao.UserDao;
import ru.otus.hw17.api.model.User;

import java.util.Optional;

public class DbServiceUserImpl implements DBServiceUser {
  private static Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);

  private final UserDao userDao;

  public DbServiceUserImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public void saveUser(User user) {
    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        userDao.saveUser(user);
        sessionManager.commitSession();
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollbackSession();
        throw new DbServiceException(e);
      }
    }
  }


  @Override
  public Optional<User> getUser(long id) {
    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        Optional<User> userOptional = userDao.getUser(id);

        logger.info("user: {}", userOptional.orElse(null));
        return userOptional;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollbackSession();
      }
      return Optional.empty();
    }
  }

}
