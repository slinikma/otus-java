package ru.otus.hw19.api.sessionmanager;

public interface SessionManager extends AutoCloseable {
  void beginSession();
  void commitSession();
  void rollbackSession();
  void close();

  DatabaseSession getCurrentSession();
}
