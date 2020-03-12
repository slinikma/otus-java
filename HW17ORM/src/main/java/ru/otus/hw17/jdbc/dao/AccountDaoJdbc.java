package ru.otus.hw17.jdbc.dao;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw17.ObjectLoader;
import ru.otus.hw17.api.dao.AccountDao;
import ru.otus.hw17.api.dao.AccountDaoException;
import ru.otus.hw17.api.model.Account;
import ru.otus.hw17.api.sessionmanager.SessionManager;
import ru.otus.hw17.jdbc.DbExecutor;
import ru.otus.hw17.jdbc.sessionmanager.SessionManagerJdbc;
import ru.otus.hw17.objectvisitor.ObjectTraverseException;
import ru.otus.hw17.objectvisitor.Traverser;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@AllArgsConstructor
public class AccountDaoJdbc implements AccountDao {
  private static Logger logger = LoggerFactory.getLogger(AccountDaoJdbc.class);

  private final SessionManagerJdbc sessionManager;
  private final DbExecutor<Account> dbExecutor;


  @Override
  public Optional<Account> getAccount(long id) {
    try {
      dbExecutor.setConnection(getConnection());
      return dbExecutor.load(id, Account.class, resultSet -> {
        try {
          if (resultSet.next()) {
            return ObjectLoader.loadFromResultSet(resultSet, Account.class.getConstructor().newInstance());
          }
        } catch (SQLException | NoSuchMethodException | ObjectTraverseException e) {
          logger.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        } catch (InstantiationException e) {
          e.printStackTrace();
        } catch (InvocationTargetException e) {
          e.printStackTrace();
        }
        return null;
      });
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return Optional.empty();
  }

  @Override
  public long saveAccount(Account account) {
    try {
      dbExecutor.setConnection(getConnection());
      return dbExecutor.create(account);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new AccountDaoException(e);
    }
  }

  @Override
  public void updateAccount(Account account) {
    try {
      dbExecutor.setConnection(getConnection());
      dbExecutor.update(account);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new AccountDaoException(e);
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
