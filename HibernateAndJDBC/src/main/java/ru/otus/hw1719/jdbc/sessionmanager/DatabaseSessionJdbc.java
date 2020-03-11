package ru.otus.hw1719.jdbc.sessionmanager;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.otus.hw1719.api.sessionmanager.DatabaseSession;

import java.sql.Connection;
import java.sql.SQLException;

// Для JDBC DatabaseSession определён коннектом к базе. Так что храним просто коннект

@AllArgsConstructor
public class DatabaseSessionJdbc implements DatabaseSession {

  private static final int TIMEOUT_IN_SECONDS = 5;
  @Getter private final Connection connection;

  // Это  вообще норм практика - делать ф-ию, которая не возвращает Boolean, а кидает исключение?
  public void checkConnection() throws SQLException {
      if (connection == null || !connection.isValid(TIMEOUT_IN_SECONDS)) {
        throw new SQLException("Connection is invalid");
      }
  }
}
