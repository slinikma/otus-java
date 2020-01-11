package ru.otus.hw17.jdbc.dao;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw17.api.dao.UserDao;
import ru.otus.hw17.api.dao.UserDaoException;
import ru.otus.hw17.api.model.User;
import ru.otus.hw17.api.sessionmanager.SessionManager;
import ru.otus.hw17.jdbc.DbExecutor;
import ru.otus.hw17.jdbc.sessionmanager.SessionManagerJdbc;

import java.sql.Connection;
import java.util.Optional;

@AllArgsConstructor
public class UserDaoJdbc implements UserDao {
  private static Logger logger = LoggerFactory.getLogger(UserDaoJdbc.class);

  private final SessionManagerJdbc sessionManager;
  private final DbExecutor<User> dbExecutor;

  @Override
  public Optional<User> getUser(long id) {
    try {
      dbExecutor.setConnection(getConnection());
      return dbExecutor.load(id, ru.otus.hw17.api.model.myorm.User.class);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return Optional.empty();
  }

  @Override
  public long saveUser(User user) {
    try {
      // TODO: вызываем visitor с сервисом получаения SQL запроса
      // TODO: переписываю executor с методами CRUCL и тут вызываю эти методы. рефлексию вызываю уже там и строю запросы. Id нужен не для вставки (там он игнорится просто, т.к. автоинкремент), а для селекта\апдейта
      // TODO: нужно ли рассмотреть случаи вставки без автоинкремента?
      dbExecutor.setConnection(getConnection());
      return dbExecutor.create(user);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new UserDaoException(e);
    }
  }

  @Override
  public void updateUser(User user) {
    try {
      dbExecutor.setConnection(getConnection());
      dbExecutor.update(user);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new UserDaoException(e);
    }
  }

  @Override
  public SessionManager getSessionManager() {
    return sessionManager;
  }

  private Connection getConnection() {
    return sessionManager.getCurrentSession().getConnection();
  }
}