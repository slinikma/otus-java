package ru.otus.HW28MessageSystem.controllers;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.HW28MessageSystem.db.DBService;
import ru.otus.HW28MessageSystem.db.handlers.CreateUserRequestHandler;
import ru.otus.HW28MessageSystem.db.handlers.GetAllUsersDataRequestHandler;
import ru.otus.HW28MessageSystem.domain.User;
import ru.otus.HW28MessageSystem.front.FrontendService;
import ru.otus.HW28MessageSystem.front.FrontendServiceImpl;
import ru.otus.HW28MessageSystem.front.handlers.CreateUserResponseHandler;
import ru.otus.HW28MessageSystem.front.handlers.GetAllUsersDataResponseHandler;
import ru.otus.HW28MessageSystem.messagesystem.*;

import javax.annotation.PostConstruct;

@Slf4j
@RestController
public class AdminController {

  private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

  private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
  private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";

  @Autowired
  MessageSystem messageSystem;

  @Autowired
  DBService dbService;

  // Исполльзование messaging template (spring in action 4th, chapter 18)
  @Autowired
  private SimpMessageSendingOperations messaging;

  private FrontendService frontendService;


  @PostConstruct
  public void init() {

    MsClient databaseMsClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME, messageSystem);
    databaseMsClient.addHandler(MessageType.USER_DATA, new CreateUserRequestHandler(dbService));
    databaseMsClient.addHandler(MessageType.USERS_LIST, new GetAllUsersDataRequestHandler(dbService));
    messageSystem.addClient(databaseMsClient);

    MsClient frontendMsClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME, messageSystem);

    frontendService = new FrontendServiceImpl(frontendMsClient, DATABASE_SERVICE_CLIENT_NAME);

    frontendMsClient.addHandler(MessageType.USER_DATA, new CreateUserResponseHandler(frontendService));
    frontendMsClient.addHandler(MessageType.USERS_LIST, new GetAllUsersDataResponseHandler(frontendService));
    messageSystem.addClient(frontendMsClient);
  }

  @MessageMapping({"/", "/admin"})
  public String adminView() {
    return "admin_main.html";
  }

  @MessageMapping("/admin/user/list")
  public void userListView() {

    // TODO: GetAllUsersDataRequestHandler
    log.info("got userListView request");
    frontendService.getAllUsers(users -> {
      logger.info("Users: {}", users);
      messaging.convertAndSend("/topic/response", users);
    });
  }

  @MessageMapping("/admin/user/create")
  public void userCreateView(User user) {
    log.info("got user: {}", user.toString());
    frontendService.saveUser(user, resultUser -> {
      logger.info("New use: {}", resultUser);
      if (user.equals(resultUser)) {
        // TODO: нужно отправлять оповещение конкетному пользователю!
        messaging.convertAndSend("/topic/response", "User was successfully created!");
      } else {
        messaging.convertAndSend("/topic/response", "User creation failure :(");
      }
    });
  }
}
