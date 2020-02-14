package ru.otus.HW28MessageSystem.controllers;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.HW28MessageSystem.domain.User;
import ru.otus.HW28MessageSystem.front.FrontendService;
import ru.otus.HW28MessageSystem.messagesystem.*;


@Slf4j
@RestController
public class AdminController {

  private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

  private final MessageSystem messageSystem;

  // Исполльзование messaging template (spring in action 4th, chapter 18)
  private final SimpMessageSendingOperations messaging;

  private final FrontendService frontendService;

  @Autowired
  public AdminController(MessageSystem messageSystem,
                         SimpMessageSendingOperations messaging,
                         FrontendService frontendService)
  {
    this.messageSystem = messageSystem;
    this.messaging = messaging;
    this.frontendService = frontendService;
  }

  @MessageMapping({"/", "/admin"})
  public String adminView() {
    return "index.html";
  }

  @MessageMapping("/admin/user/list")
  public void userListView(SimpMessageHeaderAccessor sha) {

    log.info("got userListView request");
    frontendService.getAllUsers(users -> {
      logger.info("Users: {}", users);
      // TODO: стоит ли использовать имя юзера в ID сообщения?
      messaging.convertAndSendToUser(sha.getUser().getName(), "/topic/response/user/list", users);
    });
  }

  @MessageMapping("/admin/user/create")
  public void userCreateView(SimpMessageHeaderAccessor sha, User user) {
    log.info("got user: {}", user.toString());
    frontendService.saveUser(user, result -> {
      logger.info("New use: {}", result);
      if (user.equals(result)) {
        // Оповещаем всех о новом пользователе!
        frontendService.getAllUsers(users -> {
          logger.info("Users: {}", users);
          // TODO: стоит ли использовать имя юзера в ID сообщения?
          messaging.convertAndSend("/topic/response/user/list", users);
        });
      }
    }, error -> {
      messaging.convertAndSendToUser(sha.getUser().getName(), "/topic/response/errors", error);
    });
  }

  @MessageMapping("/messagesystem/stop")
  public void stopMessageSystem(SimpMessageHeaderAccessor sha) throws InterruptedException {

    log.info("got stop signal");
    messageSystem.dispose();
    messaging.convertAndSendToUser(sha.getUser().getName(), "/topic/response/messagesystem", "Message system was stopped!");
  }

  @MessageExceptionHandler()
  @SendToUser("/topic/response/errors")
  public String handleExceptions(Throwable t) {
    logger.error("Error handling message: " + t.getMessage());
    return "Message system was stopped!";
  }
}
