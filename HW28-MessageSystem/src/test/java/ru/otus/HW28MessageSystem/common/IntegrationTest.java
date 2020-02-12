package ru.otus.HW28MessageSystem.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.HW28MessageSystem.db.DBService;
import ru.otus.HW28MessageSystem.db.handlers.CreateUserRequestHandler;
import ru.otus.HW28MessageSystem.db.handlers.GetAllUsersDataRequestHandler;
import ru.otus.HW28MessageSystem.domain.User;
import ru.otus.HW28MessageSystem.front.FrontendService;
import ru.otus.HW28MessageSystem.front.FrontendServiceImpl;
import ru.otus.HW28MessageSystem.front.handlers.CreateUserResponseHandler;
import ru.otus.HW28MessageSystem.front.handlers.GetAllUsersDataResponseHandler;
import ru.otus.HW28MessageSystem.messagesystem.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class IntegrationTest {
  private static final Logger logger = LoggerFactory.getLogger(IntegrationTest.class);

  private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
  private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";
  private static final String HANDLER_MAPPING_USER_DATA = "userData";


  private MessageSystem messageSystem;
  private FrontendService frontendService;
  private MsClient databaseMsClient;
  private MsClient frontendMsClient;

  List users = new ArrayList<User>();


//  @BeforeEach
//  public void setup() {
//    logger.info("setup");
//    messageSystem = new MessageSystemImpl();
//
//    users.add(new User("login1", "password1"));
//    users.add(new User("login2", "password2"));
//    users.add(new User("login3", "password3"));
//    users.add(new User("login4", "password4"));
//
//    databaseMsClient = spy(new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME, messageSystem));
//    DBService dbService = mock(DBService.class);
//    when(dbService.saveUser(any(User.class))).thenAnswer(invocation -> String.valueOf((Long)invocation.getArgument(0)));
//    when(dbService.getAllUsers()).thenAnswer(invocation -> users);
//    databaseMsClient.addHandler(MessageType.USER_DATA, new CreateUserRequestHandler(dbService));
//    databaseMsClient.addHandler(MessageType.USERS_LIST, new GetAllUsersDataRequestHandler(dbService));
//    messageSystem.addClient(databaseMsClient);
//
//    frontendMsClient = spy(new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME, messageSystem));
//    frontendService = new FrontendServiceImpl(frontendMsClient);
//    frontendMsClient.addHandler(MessageType.USER_DATA, new CreateUserResponseHandler(frontendService));
//    frontendMsClient.addHandler(MessageType.USERS_LIST, new GetAllUsersDataResponseHandler(frontendService));
//    messageSystem.addClient(frontendMsClient);
//
//    logger.info("setup done");
//  }


  @DisplayName("Базовый сценарий получения данных")
  @RepeatedTest(1000)
  // TODO: если тест получает каждый раз одни и те-же данные, он адекватный? что тестирую?
  public void getAllUsers() throws Exception {
    int counter = 3;
    CountDownLatch waitLatch = new CountDownLatch(counter);

    IntStream.range(0, counter).forEach(id ->
        frontendService.getAllUsers(data -> {
          assertThat(users).isEqualTo(users);
          waitLatch.countDown();
        }));

    waitLatch.await();
    messageSystem.dispose();
    logger.info("done");
  }
}
