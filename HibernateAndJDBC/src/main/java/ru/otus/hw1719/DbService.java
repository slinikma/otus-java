package ru.otus.hw1719;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbService {
  private static Logger logger = LoggerFactory.getLogger(DbService.class);

  public static void main(String[] args) throws Exception {
//    DataSource dataSource = new DataSourceH2();
//    DbService demo = new DbService();
//
//    demo.createTable(dataSource);
//
//    SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);
//
//    // User
//    DbExecutor<User> userDbExecutorbExecutor = new DbExecutor<>();
//    UserDao userDao = new UserDaoJdbc(sessionManager, userDbExecutorbExecutor);
//    DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);
//
//    dbServiceUser.saveUser(new ru.otus.hw17.api.model.myorm.User(0, "dbServiceUser"));
//    dbServiceUser.saveUser(new ru.otus.hw17.api.model.myorm.User(1, "dbServiceUser1"));
//    Optional<User> user = dbServiceUser.getUser(1);
//
//    System.out.println(user);
//    user.ifPresentOrElse(
//        crUser -> logger.info("created user, name:{}", crUser.getName()),
//        () -> logger.info("user was not created")
//    );
//
//    // Account
//    DbExecutor<Account> accountDbExecutor = new DbExecutor<>();
//    AccountDao accountDao = new AccountDaoJdbc(sessionManager, accountDbExecutor);
//    DBServiceAccount dbServiceAccount = new DBServiceAccountImpl(accountDao);
//
//    dbServiceAccount.saveAccount(new Account(0, "login1", 123));
//    dbServiceAccount.saveAccount(new Account(1, "login2", 123123));
//    Optional<Account> account = dbServiceAccount.getAccount(1);
//
//    System.out.println(account);
//    user.ifPresentOrElse(
//        crUser -> logger.info("created account, name:{}", crUser.getName()),
//        () -> logger.info("account was not created")
//    );

  }

  private void createTable(DataSource dataSource) throws SQLException {
    try (Connection connection = dataSource.getConnection();
         PreparedStatement pst = connection.prepareStatement("create table User(id long auto_increment, name varchar(50))")) {
      pst.executeUpdate();
    }
    System.out.println("User table created");

    try (Connection connection = dataSource.getConnection();
         PreparedStatement pst = connection.prepareStatement("create table Account(no long auto_increment, type varchar(50), rest int)")) {
      pst.executeUpdate();
    }
    System.out.println("Account table created");
  }
}
