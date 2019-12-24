package ru.otus.hw17.jdbc.dao;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw17.api.dao.AccountDao;
import ru.otus.hw17.api.dao.UserDaoException;
import ru.otus.hw17.api.model.Account;
import ru.otus.hw17.api.sessionmanager.SessionManager;
import ru.otus.hw17.jdbc.DbExecutor;
import ru.otus.hw17.jdbc.sessionmanager.SessionManagerJdbc;

import java.sql.Connection;
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
      return dbExecutor.load(id, Account.class);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return Optional.empty();
  }

  @Override
  public void saveAccount(Account account) {
    try {
      dbExecutor.setConnection(getConnection());
      dbExecutor.create(account);
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
