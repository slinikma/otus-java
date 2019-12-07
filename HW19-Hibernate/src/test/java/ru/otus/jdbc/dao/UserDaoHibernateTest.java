package ru.otus.jdbc.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.AbstractHibernateTest;
import ru.otus.hw19.api.model.User;
import ru.otus.hw19.hibernate.dao.UserDaoHibernate;
import ru.otus.hw19.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Dao для работы с пользователями должно ")
public class UserDaoHibernateTest extends AbstractHibernateTest {

  private SessionManagerHibernate sessionManagerHibernate;
  private UserDaoHibernate userDaoHibernate;

  @BeforeEach
  @Override
  public void setUp() {
    super.setUp();
    sessionManagerHibernate = new SessionManagerHibernate(sessionFactory);
    userDaoHibernate = new UserDaoHibernate(sessionManagerHibernate);
  }

  @Test
  @DisplayName(" корректно загружать пользователя по заданному id")
  void shouldFindCorrectUserById() {
    User expectedUser = new User(0, "Вася");
    saveUser(expectedUser);

    assertThat(expectedUser.getId()).isGreaterThan(0);

    sessionManagerHibernate.beginSession();
    Optional<User> mayBeUser = userDaoHibernate.findById(expectedUser.getId());
    sessionManagerHibernate.commitSession();

    assertThat(mayBeUser).isPresent().get().isEqualToComparingFieldByField(expectedUser);
  }

}
