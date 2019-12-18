package ru.otus.hw17.jdbc.dao;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw17.api.dao.AccountDao;
import ru.otus.hw17.api.model.Account;
import ru.otus.hw17.api.model.User;
import ru.otus.hw17.api.sessionmanager.SessionManager;
import ru.otus.hw17.jdbc.DbExecutor;
import ru.otus.hw17.jdbc.sessionmanager.SessionManagerJdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@AllArgsConstructor
public class AccountDaoJdbc implements AccountDao {
  private static Logger logger = LoggerFactory.getLogger(UserDaoJdbc.class);

  private final SessionManagerJdbc sessionManager;
  private final DbExecutor<Account> dbExecutor;

  @Override
  public Optional<User> findById(long id) {
    return Optional.empty();
  }

  @Override
  public long saveUser(User user) {
    return 0;
  }

  @Override
  public SessionManager getSessionManager() {
    return null;
  }

  private Connection getConnection() {
    return sessionManager.getCurrentSession().getConnection();
  }
}
