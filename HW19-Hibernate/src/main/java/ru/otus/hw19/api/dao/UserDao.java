package ru.otus.hw19.api.dao;

import ru.otus.hw19.api.sessionmanager.SessionManager;

// Интерфейс для CRU(D) API
public interface UserDao<T> {
  void create(T objectData);
  void update(T objectData);
//  void createOrUpdate(T objectData); // опционально.
  <T> T load(long id, Class<T> clazz);

  SessionManager getSessionManager();
}
