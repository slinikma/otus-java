package ru.otus.hw17.jdbc.sessionmanager;

import ru.otus.hw17.api.sessionmanager.DatabaseSession;
import ru.otus.hw17.api.sessionmanager.SessionManager;
import ru.otus.hw17.api.sessionmanager.SessionManagerException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class SessionManagerJdbc implements SessionManager {

  private final DataSource dataSource;
  private DatabaseSessionJdbc databaseSession;

  public SessionManagerJdbc(DataSource dataSource) {
    if (dataSource == null) {
      throw new SessionManagerException("Datasource is null");
    }
    this.dataSource = dataSource;
  }

  @Override
  public void beginSession() {
    Connection connection;
    try {
      connection = dataSource.getConnection();
      databaseSession = new DatabaseSessionJdbc(connection);
    } catch (SQLException ex) {
      throw new SessionManagerException(ex);
    }
  }

  @Override
  public void commitSession() {
    try {
      // TODO: почему не boolean isConnected() ?
      databaseSession.checkConnection();
      databaseSession.getConnection().commit();
    } catch (SQLException ex) {
      throw new SessionManagerException(ex);
    }
  }

  @Override
  public void rollbackSession() {
    try {
      // TODO: почему не boolean isConnected() ?
      databaseSession.checkConnection();
      databaseSession.getConnection().rollback();
    } catch (SQLException ex) {
      throw new SessionManagerException(ex);
    }
  }

  @Override
  public void close() {
    try {
      // TODO: почему не boolean isConnected() ?
      databaseSession.checkConnection();
      databaseSession.getConnection().close();
    } catch (SQLException ex) {
      throw new SessionManagerException(ex);
    }
  }

  @Override
  public DatabaseSessionJdbc getCurrentSession() {
    try {
      // TODO: почему не boolean isConnected() ?
      databaseSession.checkConnection();
      return databaseSession;
    } catch (SQLException ex) {
      throw new SessionManagerException(ex);
    }
  }
}
