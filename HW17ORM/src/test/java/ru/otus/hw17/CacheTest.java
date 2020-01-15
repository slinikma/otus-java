package ru.otus.hw17;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw17.api.dao.UserDao;
import ru.otus.hw17.api.model.User;
import ru.otus.hw17.api.service.DBServiceUser;
import ru.otus.hw17.api.service.DbServiceUserImpl;
import ru.otus.hw17.jdbc.sessionmanager.SessionManagerJdbc;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CacheTest {
  private static final Logger logger = LoggerFactory.getLogger(CacheTest.class);
  private DBServiceUser dbServiceUser;
  private static final long USER_ID = 1L;

  @Mock
  private SessionManagerJdbc sessionManager;

  @Mock
  private UserDao userDao;

  @BeforeEach
  void setUp() {
    given(userDao.getSessionManager()).willReturn(sessionManager);
    dbServiceUser = new DbServiceUserImpl(userDao);

    given(userDao.saveUser(any())).willReturn(USER_ID);
    dbServiceUser.saveUser(new User(USER_ID, "Вася"));
  }

  @RepeatedTest(3)
  @DisplayName(" [с кэшом] корректно загружать пользователя по заданному id из ООООЧЕНЬ медленной БД")
  void shouldLoadCorrectUserByIdWithCache() throws InterruptedException {
    User expectedUser = new User(USER_ID, "Вася");

    // Т.к. кэш в сервисе, мокаем DAO
    given(userDao.getUser(anyLong())).will((Answer<Optional<User>>) invocationOnMock -> {
      Thread.sleep(5000);
      return Optional.ofNullable(expectedUser);
    });
    Optional<User> mayBeUser = dbServiceUser.getUser(USER_ID);
    assertThat(mayBeUser).isPresent().get().isEqualToComparingFieldByField(expectedUser);
  }

  @RepeatedTest(3)
  @DisplayName(" [без кэша] корректно загружать пользователя по заданному id из ООООЧЕНЬ медленной БД")
  void shouldLoadCorrectUserByIdWithoutCache() throws InterruptedException {
    User expectedUser = new User(USER_ID, "Вася");

    System.gc();
    Thread.sleep(2000);

    // Т.к. кэш в сервисе, мокаем DAO
    given(userDao.getUser(anyLong())).will((Answer<Optional<User>>) invocationOnMock -> {
      Thread.sleep(5000);
      return Optional.ofNullable(expectedUser);
    });
    Optional<User> mayBeUser = dbServiceUser.getUser(USER_ID);
    assertThat(mayBeUser).isPresent().get().isEqualToComparingFieldByField(expectedUser);
  }
}
