package ru.otus.HW28MessageSystem.controllers;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.HW28MessageSystem.db.handlers.GetAllUsersDataRequestHandler;
import ru.otus.HW28MessageSystem.db.handlers.GetUserDataRequestHandler;
import ru.otus.HW28MessageSystem.domain.User;
import ru.otus.HW28MessageSystem.front.FrontendService;
import ru.otus.HW28MessageSystem.front.FrontendServiceImpl;
import ru.otus.HW28MessageSystem.front.handlers.GetUserDataResponseHandler;
import ru.otus.HW28MessageSystem.messagesystem.*;

import org.jetbrains.annotations.NotNull;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@RestController
//@RequiredArgsConstructor
public class AdminController {

  // Вместо репозитория напрямую, работаем с MessageSystem
//  private final UserRepository userRepository;

  private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

  private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
  private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";

  @Autowired
  MessageSystem messageSystem;

//  @Qualifier("GetUserDataRequestHandler")
//  @Autowired
//  RequestHandler getUserDataRequestHandler;
//
//  @Qualifier("GetAllUsersDataRequestHandler")
//  @Autowired
//  RequestHandler getAllUsersDataRequestHandler;

  private FrontendService frontendService;

//  public AdminController() {
//    MsClient databaseMsClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME);
//    databaseMsClient.addHandler(MessageType.USER_DATA, new GetUserDataRequestHandler());
//    databaseMsClient.addHandler(MessageType.USERS_LIST, new GetAllUsersDataRequestHandler()); // TODO: или по типу сообщения...
//    messageSystem.addClient(databaseMsClient);
//
//    MsClient frontendMsClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME);
//
//    // Рест контроллер работает через frontendService
//    frontendService = new FrontendServiceImpl(frontendMsClient, DATABASE_SERVICE_CLIENT_NAME);
//
//    frontendMsClient.addHandler(MessageType.USER_DATA, new GetUserDataResponseHandler(frontendService));
//    messageSystem.addClient(frontendMsClient);
//  }

  @PostConstruct
  public void init() {
//    LOG.info(Arrays.asList(environment.getDefaultProfiles()));
//    try {
//      messageSystem.dispose();
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }


    MsClient databaseMsClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME, messageSystem);
//    databaseMsClient.addHandler(MessageType.USER_DATA, new GetUserDataRequestHandler());
    databaseMsClient.addHandler(MessageType.USERS_LIST, new GetAllUsersDataRequestHandler()); // TODO: или по типу сообщения...
    messageSystem.addClient(databaseMsClient);

    MsClient frontendMsClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME, messageSystem);

    // Рест контроллер работает через frontendService
    frontendService = new FrontendServiceImpl(frontendMsClient, DATABASE_SERVICE_CLIENT_NAME);

    frontendMsClient.addHandler(MessageType.USER_DATA, new GetUserDataResponseHandler(frontendService));
    messageSystem.addClient(frontendMsClient);
  }

  @GetMapping(path = {"/", "/admin"})
  public String adminView() {
    return "admin_main.html";
  }

  @MessageMapping("/admin/user/list")
//  @SendTo("/topic/response")
  public List<User> userListView() {

    // TODO: GetAllUsersDataRequestHandler
    log.info("got userListView request");
    frontendService.getAllUsers(users -> {
      logger.info("Users: {}", users);
    });

    // TODO: как возвращать значение из консьюмера??
    return null;
//    return this.userRepository.findAll();
  }

  @GetMapping(path = {"/admin/user/create"})
  public String userCreateView() {
//    model.addAttribute("user", new User());
    return "admin_create_user.html";
  }

//  @PostMapping()
  @MessageMapping("/admin/user/save")
  public RedirectView userSave(@NotNull @ModelAttribute User user) {
//    this.userRepository.save(user);
    return new RedirectView("/admin/user/list", true);
  }
}
