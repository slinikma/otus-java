package ru.otus.hw17.hibernate.dao;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw17.api.dao.UserDao;
import ru.otus.hw17.api.dao.UserDaoException;
import ru.otus.hw17.api.model.User;
import ru.otus.hw17.api.sessionmanager.SessionManager;
import ru.otus.hw17.hibernate.sessionmanager.DatabaseSessionHibernate;
import ru.otus.hw17.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.Optional;

@AllArgsConstructor
public class UserDaoHibernate implements UserDao {
  private static Logger logger = LoggerFactory.getLogger(UserDaoHibernate.class);

  private final SessionManagerHibernate sessionManager;

  @Override
  public Optional<User> getUser(long id) {
    DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
    try {
      return Optional.ofNullable(currentSession.getSession().find(User.class, id));
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return Optional.empty();
  }

  @Override
  public void saveUser(User user) {
    DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
    try {
      Session hibernateSession = currentSession.getSession();
      // TODO: посмотреть ещё раз, в чём разница между merge и persist (кроме очевидной)
      if (user.getId() > 0) {
        hibernateSession.merge(user);
      } else {
        hibernateSession.persist(user);
      }
//      return user.getId();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new UserDaoException(e);
    }
  }

  @Override
  public SessionManager getSessionManager() {
    return sessionManager;
  }
}
