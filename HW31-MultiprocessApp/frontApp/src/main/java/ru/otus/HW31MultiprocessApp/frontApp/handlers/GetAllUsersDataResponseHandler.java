package ru.otus.HW31MultiprocessApp.frontApp.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.HW31MultiprocessApp.msclient.Message;
import ru.otus.HW31MultiprocessApp.msclient.RequestHandler;
import ru.otus.HW31MultiprocessApp.msclient.common.Serializers;
import ru.otus.HW31MultiprocessApp.frontApp.FrontendService;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Component("getAllUsersDataResponseHandler")
public class GetAllUsersDataResponseHandler implements RequestHandler {

  private static final Logger logger = LoggerFactory.getLogger(GetAllUsersDataResponseHandler.class);

  private final FrontendService frontendService;

  @Autowired
  public GetAllUsersDataResponseHandler(FrontendService frontendService) {
    this.frontendService = frontendService;
  }

  @Override
  public Optional<Message> handle(Message msg) {
    logger.info("new message:{}", msg);
    try {
      ArrayList usersList = Serializers.deserialize(msg.getPayload(), ArrayList.class);
      UUID sourceMessageId = msg.getSourceMessageId().orElseThrow(() -> new RuntimeException("Not found sourceMsg for message:" + msg.getId()));
      frontendService.takeConsumer(sourceMessageId, ArrayList.class).ifPresent(consumer -> consumer.accept(usersList));

    } catch (Exception ex) {
      logger.error("msg:" + msg, ex);
    }
    return Optional.empty();
  }
}
