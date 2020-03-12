package ru.otus.hw17.h2;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class DataSourceH2 implements DataSource {

  // By default, an in-memory database in H2 is discarded after the connection closes.
  // We use DB_CLOSE_DELAY=-1 to keep the content of an in-memory database as long as the virtual machine is alive
  private static final String URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";

  @Override
  public Connection getConnection() throws SQLException {
    Connection connection = DriverManager.getConnection(URL);
    connection.setAutoCommit(false);
    return connection;
  }

  @Override
  public Connection getConnection(String username, String password) {
    return null;
  }

  @Override
  public PrintWriter getLogWriter() {
    return null;
  }

  @Override
  public void setLogWriter(PrintWriter out) {
    throw new UnsupportedOperationException();

  }

  @Override
  public int getLoginTimeout() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setLoginTimeout(int seconds) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Logger getParentLogger() {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T> T unwrap(Class<T> iface) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) {
    throw new UnsupportedOperationException();
  }
}