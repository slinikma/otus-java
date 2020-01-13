package ru.otus.hw17;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.stat.EntityStatistics;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.BeforeEach;
import ru.otus.hw17.api.model.hibernate.AddressDataSet;
import ru.otus.hw17.api.model.hibernate.PhoneDataSet;
import ru.otus.hw17.api.model.User;
import ru.otus.hw17.hibernate.HibernateUtils;

public class AbstractHibernateTest {
  private static final String HIBERNATE_CFG_XML_FILE_RESOURCE = "hibernate-test.cfg.xml";

  protected static final String FIELD_ID = "id";
  protected static final String FIELD_NAME = "name";
  protected static final String TEST_USER_NAME = "Вася";
  protected static final String TEST_USER_NEW_NAME = "НЕ Вася";


  protected SessionFactory sessionFactory;

  @BeforeEach
  public void setUp() {
    sessionFactory = HibernateUtils.buildSessionFactory(HIBERNATE_CFG_XML_FILE_RESOURCE,
        User.class,
        PhoneDataSet.class,
        AddressDataSet.class);
  }

  protected User buildDefaultUser() {
//    AddressDataSet address = new AddressDataSet();
//    PhoneDataSet phoneNumber1 = new PhoneDataSet();
//    PhoneDataSet phoneNumber2 = new PhoneDataSet();
//    0, TEST_USER_NAME
    return new User();
  }

  protected void saveUser(User user) {
    try (Session session = sessionFactory.openSession()) {
      saveUser(session, user);
    }
  }

  protected void saveUser(Session session, User user) {
    session.beginTransaction();
    session.save(user);
    session.getTransaction().commit();
  }

  protected User loadUser(long id) {
    try (Session session = sessionFactory.openSession()) {
      return session.find(User.class, id);
    }
  }

  protected EntityStatistics getUserStatistics() {
    Statistics stats = sessionFactory.getStatistics();
    return stats.getEntityStatistics(User.class.getName());
  }
}
