package ru.otus.hw17;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw17.api.model.User;
import ru.otus.hw17.api.model.hibernate.AddressDataSet;
import ru.otus.hw17.api.model.hibernate.PhoneDataSet;
import ru.otus.hw17.hibernate.dao.UserDaoHibernate;
import ru.otus.hw17.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с пользователями должно ")
public class UserDbHibernateDaoTest extends AbstractHibernateTest {

  private SessionManagerHibernate sessionManagerHibernate;
  private UserDaoHibernate userDaoHibernate;

  @BeforeEach
  @Override
  public void setUp() {
    super.setUp();
    sessionManagerHibernate = new SessionManagerHibernate(sessionFactory);
    userDaoHibernate = new UserDaoHibernate(sessionManagerHibernate);
  }

  @DisplayName(" корректно сохранять пользователя")
  @Test
  void shouldCorrectSaveUser() {
    // Используем id 0, как незаданный id
    AddressDataSet address = new AddressDataSet(0, "My Street");

    // В качестве User передаю null
    // Можно использовать пустой конструктор, а потом через сеттеры задавать все поля, но я буду задавать только пользователя
    PhoneDataSet mtsNumber = new PhoneDataSet(0, "88005553535", null);
    PhoneDataSet tele2Number = new PhoneDataSet(0, "89005553535", null);

    List<PhoneDataSet> numberList = new ArrayList<>();
    // Используем id 0, как незаданный id
    numberList.add(mtsNumber);
    numberList.add(tele2Number);

    // Используем id 0, как незаданный id
    User expectedUser = new User(0, "Вася", address, numberList);

    // После того, как создан объект пользователя, задаём его
    mtsNumber.setUser(expectedUser);
    tele2Number.setUser(expectedUser);

    sessionManagerHibernate.beginSession(); // Можно вынести в сервис
    // Сохранение через мой DAO
    long id = userDaoHibernate.saveUser(expectedUser);
    sessionManagerHibernate.commitSession();

    // Получение напрямую через EntityManager
    User actualUser = loadUser(id);

    assertThat(actualUser).isNotNull().hasFieldOrPropertyWithValue("name", ((User)expectedUser).getName());

    assertThat(actualUser).isEqualToComparingFieldByField(expectedUser);
  }

  @Test
  @DisplayName(" корректно загружать пользователя по заданному id")
  void shouldFindCorrectUserById() {
    // Используем id 0, как незаданный id
    AddressDataSet address = new AddressDataSet(0, "My Street");

    // В качестве User передаю null
    // Можно использовать пустой конструктор, а потом через сеттеры задавать все поля, но я буду задавать только пользователя
    PhoneDataSet mtsNumber = new PhoneDataSet(0, "88005553535", null);
    PhoneDataSet tele2Number = new PhoneDataSet(0, "89005553535", null);

    List<PhoneDataSet> numberList = new ArrayList<>();
    // Используем id 0, как незаданный id
    numberList.add(mtsNumber);
    numberList.add(tele2Number);

    // Используем id 0, как незаданный id
    User expectedUser = new User(0, "Вася", address, numberList);

    // После того, как создан объект пользователя, задаём его
    mtsNumber.setUser((User) expectedUser);
    tele2Number.setUser((User) expectedUser);


    // Сохранение напрямую через EntityManager
    saveUser(expectedUser);

    assertThat(((User)expectedUser).getId()).isGreaterThan(0);

    sessionManagerHibernate.beginSession();
    // Получение пользователя через мой DAO
    Optional<User> mayBeUser = userDaoHibernate.getUser(((User)expectedUser).getId());
    sessionManagerHibernate.commitSession();

    assertThat(mayBeUser).isPresent().get().isEqualToComparingFieldByField(expectedUser);
  }


  @DisplayName(" возвращать менеджер сессий")
  @Test
  void getSessionManager() {
    assertThat(userDaoHibernate.getSessionManager()).isNotNull().isEqualTo(sessionManagerHibernate);
  }

}
