package ru.otus.HW28MessageSystem.front.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import ru.otus.HW28MessageSystem.common.Serializers;
import ru.otus.HW28MessageSystem.domain.User;
import ru.otus.HW28MessageSystem.front.FrontendService;
import ru.otus.HW28MessageSystem.messagesystem.Message;
import ru.otus.HW28MessageSystem.messagesystem.RequestHandler;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

//@Component("getAllUsersDataResponseHandler")
@Configurable
public class GetAllUsersDataResponseHandler implements RequestHandler {

  private static final Logger logger = LoggerFactory.getLogger(GetAllUsersDataResponseHandler.class);

  private FrontendService frontendService;

  @Autowired
  public GetAllUsersDataResponseHandler(FrontendService frontendService) {
    this.frontendService = frontendService;
  }

  @Override
  public Optional<Message> handle(Message msg) {
    logger.info("new message:{}", msg);
    try {
      List<User> usersList = Serializers.deserialize(msg.getPayload(), List.class);
      UUID sourceMessageId = msg.getSourceMessageId().orElseThrow(() -> new RuntimeException("Not found sourceMsg for message:" + msg.getId()));
      frontendService.takeConsumer(sourceMessageId, List.class).ifPresent(consumer -> consumer.accept(usersList));

    } catch (Exception ex) {
      logger.error("msg:" + msg, ex);
    }
    return Optional.empty();
  }
}
