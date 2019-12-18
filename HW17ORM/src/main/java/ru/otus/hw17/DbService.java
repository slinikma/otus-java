package ru.otus.hw17;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw17.api.dao.UserDao;
import ru.otus.hw17.api.model.User;
import ru.otus.hw17.api.service.DBServiceUser;
import ru.otus.hw17.api.service.DbServiceUserImpl;
import ru.otus.hw17.h2.DataSourceH2;
import ru.otus.hw17.jdbc.DbExecutor;
import ru.otus.hw17.jdbc.dao.UserDaoJdbc;
import ru.otus.hw17.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class DbService {
  private static Logger logger = LoggerFactory.getLogger(DbService.class);

  public static void main(String[] args) throws Exception {
    DataSource dataSource = new DataSourceH2();
    DbService demo = new DbService();

    demo.createTable(dataSource);

    SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);
    DbExecutor<User> dbExecutor = new DbExecutor<>();
    UserDao userDao = new UserDaoJdbc(sessionManager, dbExecutor);
    DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);
    long id = dbServiceUser.saveUser(new User(0, "dbServiceUser"));
    long id1 = dbServiceUser.saveUser(new User(1, "dbServiceUser1"));
    Optional<User> user = dbServiceUser.getUser(1);

    System.out.println(user);
    user.ifPresentOrElse(
        crUser -> logger.info("created user, name:{}", crUser.getName()),
        () -> logger.info("user was not created")
    );

  }

  private void createTable(DataSource dataSource) throws SQLException {
    try (Connection connection = dataSource.getConnection();
         PreparedStatement pst = connection.prepareStatement("create table User(id long auto_increment, name varchar(50), alias varchar(50))")) {
      pst.executeUpdate();
    }
    System.out.println("table created");
  }
}
