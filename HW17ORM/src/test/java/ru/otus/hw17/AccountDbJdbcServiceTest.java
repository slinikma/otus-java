package ru.otus.hw17;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw17.api.dao.AccountDao;
import ru.otus.hw17.api.model.myorm.Account;
import ru.otus.hw17.api.service.DBServiceAccount;
import ru.otus.hw17.api.service.DBServiceAccountImpl;
import ru.otus.hw17.h2.DataSourceH2;
import ru.otus.hw17.jdbc.DbExecutor;
import ru.otus.hw17.jdbc.dao.AccountDaoJdbc;
import ru.otus.hw17.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DbExecutor должен корректно выполнять следующие операции над таблицей Account:")
public class AccountDbJdbcServiceTest {

  private DBServiceAccount dbServiceAccount;

  @BeforeEach
  void setUp() {
    DataSource dataSource = new DataSourceH2();
    SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);
    DbExecutor<Account> AccountDbExecutorbExecutor = new DbExecutor<>();
    AccountDao AccountDao = new AccountDaoJdbc(sessionManager, AccountDbExecutorbExecutor);
    dbServiceAccount = new DBServiceAccountImpl(AccountDao);

    try {
      this.dropAccountTableIfExists(dataSource);
      this.createAccountTable(dataSource);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Test
  @DisplayName("> Добавлять нового пользователя из объекта класса Account ...")
  void shouldCreateAccount() {
    Account expectedAccount1 = new Account("Credit", 123);
    Account expectedAccount2 = new Account("Debit", 1234);
    Account expectedAccount3 = new Account("Супер-пупер", 12345);

    long accountId1 = dbServiceAccount.saveAccount(expectedAccount1);
    long accountId2 = dbServiceAccount.saveAccount(expectedAccount2);
    long accountId3 = dbServiceAccount.saveAccount(expectedAccount3);

    Optional<Account> actualAccount1 = dbServiceAccount.getAccount(accountId1);
    Optional<Account> actualAccount2 = dbServiceAccount.getAccount(accountId2);
    Optional<Account> actualAccount3 = dbServiceAccount.getAccount(accountId3);

    assertThat(actualAccount1).isNotEmpty().get() .hasFieldOrPropertyWithValue("type", expectedAccount1.getType());
    assertThat(actualAccount2).isNotEmpty().get() .hasFieldOrPropertyWithValue("type", expectedAccount2.getType());
    assertThat(actualAccount3).isNotEmpty().get() .hasFieldOrPropertyWithValue("type", expectedAccount3.getType());
  }

  @Test
  @DisplayName("> Обновлять информацию о существующем пользователе из объекта класса Account ...")
  void shouldUpdateAccount() {
    Account account1 = new Account("Credit", 123);
    Account account2 = new Account("Debit", 1234);
    Account account3 = new Account("Супер-пупер", 12345);

    long accountId1 = dbServiceAccount.saveAccount(account1);
    long accountId2 = dbServiceAccount.saveAccount(account2);
    long accountId3 = dbServiceAccount.saveAccount(account3);

    Account updatedAccount1 = new Account(accountId1, "Not Credit", 123);
    Account updatedAccount2 = new Account(accountId2, "Not Debit", 1234);
    Account updatedAccount3 = new Account(accountId3, "НЕ Супер-пупер", 12345);

    dbServiceAccount.updateAccount(updatedAccount1);
    dbServiceAccount.updateAccount(updatedAccount2);
    dbServiceAccount.updateAccount(updatedAccount3);

    Optional<Account> actualAccount1 = dbServiceAccount.getAccount(accountId1);
    Optional<Account> actualAccount2 = dbServiceAccount.getAccount(accountId2);
    Optional<Account> actualAccount3 = dbServiceAccount.getAccount(accountId3);

    assertThat(actualAccount1).isNotEmpty().get() .hasFieldOrPropertyWithValue("type", "Not Credit");
    assertThat(actualAccount2).isNotEmpty().get() .hasFieldOrPropertyWithValue("type", "Not Debit");
    assertThat(actualAccount3).isNotEmpty().get() .hasFieldOrPropertyWithValue("type", "НЕ Супер-пупер");
  }

  @Test
  @DisplayName("> Загружать данные пользователя в объект класса Account ...")
  void shouldLoadAccount() {
    // TODO: проверяется в shouldCreateAccount (хотя можно придумать и тут тестик)
  }

  // TODO: optional
//  @Test
//  @DisplayName("> Елси пользователь с заданным Id существует - обновлять о нём информацию из объекта класса Account, иначе \n " +
//      " добавлять нового пользователя из объекта класса Account ...")
//  void shouldCreateOrUpdateAccount() {
//
//  }

  private static void createAccountTable(DataSource dataSource) throws SQLException {
    try (Connection connection = dataSource.getConnection();
         PreparedStatement pst = connection.prepareStatement("create table Account(no long auto_increment, type varchar(50), rest int)")) {
      pst.executeUpdate();
    }
    System.out.println("Account table created");
  }

  private static void dropAccountTableIfExists(DataSource dataSource) throws SQLException {
    try (Connection connection = dataSource.getConnection();
         PreparedStatement pst = connection.prepareStatement("drop table if exists Account")) {
      pst.executeUpdate();
    }
    System.out.println("User table dropped");
  }
}
