package ru.otus.hw1719.hibernate.sessionmanager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.otus.hw1719.api.sessionmanager.SessionManager;
import ru.otus.hw1719.api.sessionmanager.SessionManagerException;

public class SessionManagerHibernate implements SessionManager {

  private DatabaseSessionHibernate databaseSessionHibernate;
  private final SessionFactory sessionFactory;

  public SessionManagerHibernate(SessionFactory sessionFactory) {
    if (sessionFactory == null) {
      throw new SessionManagerException("SessionFactory is null");
    }
    this.sessionFactory = sessionFactory;
  }

  @Override
  public void beginSession() {
    try {
      databaseSessionHibernate = new DatabaseSessionHibernate(sessionFactory.openSession());
    } catch (Exception e) {
      throw new SessionManagerException(e);
    }
  }

  @Override
  public void commitSession() {
    checkSessionAndTransaction();
    try {
      // В нашем случае транзакция = сессия
      databaseSessionHibernate.getTransaction().commit();
      databaseSessionHibernate.getSession().close();
    } catch (Exception e) {
      throw new SessionManagerException(e);
    }
  }

  @Override
  public void rollbackSession() {
    checkSessionAndTransaction();
    try {
      // В нашем случае транзакция = сессия
      databaseSessionHibernate.getTransaction().rollback();
      databaseSessionHibernate.getSession().close();
    } catch (Exception e) {
      throw new SessionManagerException(e);
    }
  }

  @Override
  public void close() {
    // TODO: почему тут альтернативный checkSessionAndTransaction, но не функцией?
    if (databaseSessionHibernate == null) {
      return;
    }
    Session session = databaseSessionHibernate.getSession();
    if (session == null || !session.isConnected()) {
      return;
    }

    Transaction transaction = databaseSessionHibernate.getTransaction();
    if (transaction == null || !transaction.isActive()) {
      return;
    }

    try {
      // TODO: с тем же успехом можно было вынести rollback и commit в DatabaseSessionHibernate
      databaseSessionHibernate.close();
      databaseSessionHibernate = null;
    } catch (Exception e) {
      throw new SessionManagerException(e);
    }
  }

  @Override
  public DatabaseSessionHibernate getCurrentSession() {
    checkSessionAndTransaction();
    return databaseSessionHibernate;
  }

  // TODO: почему не вынести проверку в  DatabaseSessionHibernate?
  private void checkSessionAndTransaction() {
    if (databaseSessionHibernate == null) {
      throw new SessionManagerException("DatabaseSession not opened ");
    }
    Session session = databaseSessionHibernate.getSession();
    if (session == null || !session.isConnected()) {
      throw new SessionManagerException("Session not opened ");
    }

    Transaction transaction = databaseSessionHibernate.getTransaction();
    if (transaction == null || !transaction.isActive()) {
      throw new SessionManagerException("Transaction not opened ");
    }
  }
}
