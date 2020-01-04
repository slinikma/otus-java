package ru.otus.hw17;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

import static org.assertj.core.api.Assertions.*;

@DisplayName("DbExecutor должен корректно выполнять следующие операции над таблицей User:")
public class UserDbJdbcServiceTest {

  private DBServiceUser dbServiceUser;

  @BeforeEach
  void setUp() {
    DataSource dataSource = new DataSourceH2();
    SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);
    DbExecutor<User> userDbExecutorbExecutor = new DbExecutor<>();
    UserDao userDao = new UserDaoJdbc(sessionManager, userDbExecutorbExecutor);
    dbServiceUser = new DbServiceUserImpl(userDao);

    try {
      this.dropUseTableIfExists(dataSource);
      this.createUserTable(dataSource);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Test
  @DisplayName("> Добавлять нового пользователя из объекта класса User ...")
  void shouldCreateUser() {
    User expectedUser1 = new ru.otus.hw17.api.model.myorm.User(0, "Вася");
    User expectedUser2 = new ru.otus.hw17.api.model.myorm.User(1, "Никита");
    User expectedUser3 = new ru.otus.hw17.api.model.myorm.User(2, "Петя");

    dbServiceUser.saveUser(expectedUser1);
    dbServiceUser.saveUser(expectedUser2);
    dbServiceUser.saveUser(expectedUser3);

    Optional<User> actualUser1 = dbServiceUser.getUser(0);
    Optional<User> actualUser2 = dbServiceUser.getUser(1);
    Optional<User> actualUser3 = dbServiceUser.getUser(2);

    assertThat(actualUser1).isNotEmpty().get().hasFieldOrPropertyWithValue("name", ((ru.otus.hw17.api.model.myorm.User)expectedUser1).getName());
    assertThat(actualUser2).isNotEmpty().get().hasFieldOrPropertyWithValue("name", ((ru.otus.hw17.api.model.myorm.User)expectedUser2).getName());
    assertThat(actualUser3).isNotEmpty().get().hasFieldOrPropertyWithValue("name", ((ru.otus.hw17.api.model.myorm.User)expectedUser3).getName());
  }

  @Test
  @DisplayName("> Обновлять информацию о существующем пользователе из объекта класса User ...")
  void shouldUpdateUser() {
    User user1 = new ru.otus.hw17.api.model.myorm.User(0, "Вася");
    User user2 = new ru.otus.hw17.api.model.myorm.User(1, "Никита");
    User user3 = new ru.otus.hw17.api.model.myorm.User(2, "Петя");

    dbServiceUser.saveUser(user1);
    dbServiceUser.saveUser(user2);
    dbServiceUser.saveUser(user3);

    User updatedUser1 = new ru.otus.hw17.api.model.myorm.User(0, "Не Вася");
    User updatedUser2 = new ru.otus.hw17.api.model.myorm.User(1, "Не Никита");
    User updatedUser3 = new ru.otus.hw17.api.model.myorm.User(2, "Не Петя");

    dbServiceUser.updateUser(updatedUser1);
    dbServiceUser.updateUser(updatedUser2);
    dbServiceUser.updateUser(updatedUser3);

    Optional<User> actualUser1 = dbServiceUser.getUser(0);
    Optional<User> actualUser2 = dbServiceUser.getUser(1);
    Optional<User> actualUser3 = dbServiceUser.getUser(2);

    assertThat(actualUser1).isNotEmpty().get().hasFieldOrPropertyWithValue("name", "Не Вася");
    assertThat(actualUser2).isNotEmpty().get().hasFieldOrPropertyWithValue("name", "Не Никита");
    assertThat(actualUser3).isNotEmpty().get().hasFieldOrPropertyWithValue("name", "Не Петя");
  }

  @Test
  @DisplayName("> Загружать данные пользователя в объект класса User ...")
  void shouldLoadUser() {
    // TODO: проверяется в shouldCreateUser (хотя можно придумать и тут тестик)
  }

  // TODO: optional
//  @Test
//  @DisplayName("> Елси пользователь с заданным Id существует - обновлять о нём информацию из объекта класса User, иначе \n " +
//      " добавлять нового пользователя из объекта класса User ...")
//  void shouldCreateOrUpdateUser() {
//
//  }

  private static void createUserTable(DataSource dataSource) throws SQLException {
    try (Connection connection = dataSource.getConnection();
         PreparedStatement pst = connection.prepareStatement("create table User(id long auto_increment, name varchar(50))")) {
      pst.executeUpdate();
    }
    System.out.println("User table created");
  }

  private static void dropUseTableIfExists(DataSource dataSource) throws SQLException {
    try (Connection connection = dataSource.getConnection();
         PreparedStatement pst = connection.prepareStatement("drop table if exists User")) {
      pst.executeUpdate();
    }
    System.out.println("User table dropped");
  }
}
