package ru.otus.hw17.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw17.api.model.User;
import ru.otus.hw17.api.sessionmanager.SessionManager;
import ru.otus.hw17.api.dao.UserDao;
import ru.otus.hw21.Cache;
import ru.otus.hw21.CacheListener;
import ru.otus.hw21.MyCache;

import java.util.Optional;

public class DbServiceUserImpl implements DBServiceUser {
  private static Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);

  private final UserDao userDao;
  private final Cache<String, User> cache;

  public DbServiceUserImpl(UserDao userDao) {
    this.userDao = userDao;

    CacheListener<String, User> сacheListener =
        (key, value, action) -> logger.info("key:{}, value:{}, action: {}",  key, value, action);
    cache = new MyCache();
    cache.addListener(сacheListener);
  }

  @Override
  public long saveUser(User user) {
    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        long id = userDao.saveUser(user);
        sessionManager.commitSession();

        // Кладём объект в кеш
        cache.put(String.valueOf(user.getId()), user);

        return id;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollbackSession();
        throw new DbServiceException(e);
      }
    }
  }

  @Override
  public void updateUser(User user) {
    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        userDao.updateUser(user);
        sessionManager.commitSession();

        // Обновляем объект в кеше
        cache.put(String.valueOf(user.getId()), user);
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollbackSession();
        throw new DbServiceException(e);
      }
    }
  }


  @Override
  public Optional<User> getUser(long id) {

    // Если есть в кэше, возвращаем из него
    User user = cache.get(String.valueOf(id));
    if (user != null) {
      return Optional.ofNullable(user);
    }

    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        Optional<User> userOptional = userDao.getUser(id);

        // Обновляем объект в кеше, т.к. был обращение
        cache.put(String.valueOf(userOptional.get().getId()), userOptional.get());

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
