package ru.otus.hw19.api.service;

import ru.otus.hw19.api.dao.UserDao;
import ru.otus.hw19.api.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw19.api.sessionmanager.SessionManager;

import java.util.Optional;

public class DbServiceUserImpl implements DBServiceUser {
  private static Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);

  private final UserDao userDao;

  public DbServiceUserImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public void update(User user) {
    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        userDao.update(user);
        sessionManager.commitSession();

//        logger.info("created user: {}", userId);
//        return userId;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollbackSession();
        throw new DbServiceException(e);
      }
    }
  }

  @Override
  public Optional<User> getUser(long id) {
//    try (SessionManager sessionManager = userDao.getSessionManager()) {
//      sessionManager.beginSession();
//      try {
////        Optional<User> userOptional = userDao.findById(id);
//
//        logger.info("user: {}", userOptional.orElse(null));
//        return userOptional;
//      } catch (Exception e) {
//        logger.error(e.getMessage(), e);
//        sessionManager.rollbackSession();
//      }
//      return Optional.empty();
//    }
    return Optional.empty();
  }
}
