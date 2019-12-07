package ru.otus.hw19;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw19.api.dao.UserDao;
import ru.otus.hw19.api.model.User;
import ru.otus.hw19.api.service.DBServiceUser;
import ru.otus.hw19.api.service.DbServiceUserImpl;
import ru.otus.hw19.hibernate.HibernateUtils;
import ru.otus.hw19.hibernate.dao.UserDaoHibernate;
import ru.otus.hw19.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.Optional;

public class DbServiceDemo {
  private static Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

  public static void main(String[] args) {
    // Все главное см в тестах
    SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class);

    SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
    UserDao userDao = new UserDaoHibernate(sessionManager);
    DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);


    dbServiceUser.update(new User(0, "Вася"));
//    Optional<User> mayBeCreatedUser = dbServiceUser.getUser(id);

//    id = dbServiceUser.saveUser(new User("1L", "А! Нет. Это же совсем не Вася"));
//    Optional<User> mayBeUpdatedUser = dbServiceUser.getUser(id);

//    outputUserOptional("Created user", mayBeCreatedUser);
//    outputUserOptional("Updated user", mayBeUpdatedUser);
  }

  private static void outputUserOptional(String header, Optional<User> mayBeUser) {
    System.out.println("-----------------------------------------------------------");
    System.out.println(header);
    mayBeUser.ifPresentOrElse(System.out::println, () -> logger.info("User not found"));
  }
}
