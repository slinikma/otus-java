package ru.otus.hw19.hibernate.dao;

import ru.otus.hw19.api.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw19.api.sessionmanager.SessionManager;
import ru.otus.hw19.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.List;
import java.util.ArrayList;


public class UserDaoHibernate implements ru.otus.hw19.api.dao.UserDao {
  private static Logger logger = LoggerFactory.getLogger(UserDaoHibernate.class);

  private final SessionManagerHibernate sessionManager;

  public UserDaoHibernate(SessionManagerHibernate sessionManager) {
    this.sessionManager = sessionManager;
  }

  private List<User> users = new ArrayList<>();

//  public UserDaoHibernate() {
//    users.add(new User("John", "john@domain.com"));
//    users.add(new User("Susan", "susan@domain.com"));
//  }

  @Override
  public void create(Object objectData) {

  }

  @Override
  public void update(Object objectData) {
  }

  @Override
  public Object load(long id, Class clazz) {
    return null;
  }

  @Override
  public SessionManager getSessionManager() {
    return sessionManager;
  }
}
