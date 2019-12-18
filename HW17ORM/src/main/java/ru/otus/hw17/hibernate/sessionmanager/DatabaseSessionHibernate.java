package ru.otus.hw17.hibernate.sessionmanager;

import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.otus.hw17.api.sessionmanager.DatabaseSession;

// Для хибернейта определяем сессию как транзакцию.
public class DatabaseSessionHibernate implements DatabaseSession {
  @Getter private final Session session;
  @Getter private final Transaction transaction;

  DatabaseSessionHibernate(Session session) {
    this.session = session;
    this.transaction = session.beginTransaction();
  }

  public void close() {
    if (transaction.isActive()) {
      transaction.commit();
    }
    session.close();
  }
}
