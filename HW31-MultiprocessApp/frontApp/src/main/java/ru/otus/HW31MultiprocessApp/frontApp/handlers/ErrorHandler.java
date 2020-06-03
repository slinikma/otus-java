package ru.otus.HW31MultiprocessApp.frontApp.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.HW31MultiprocessApp.msclient.Message;
import ru.otus.HW31MultiprocessApp.msclient.RequestHandler;
import ru.otus.HW31MultiprocessApp.msclient.common.Serializers;
import ru.otus.HW31MultiprocessApp.frontApp.FrontendService;

import java.util.Optional;
import java.util.UUID;

@Component("errorHandler")
public class ErrorHandler implements RequestHandler {

  private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

  private final FrontendService frontendService;

  @Autowired
  public ErrorHandler(FrontendService frontendService) {
    this.frontendService = frontendService;
  }

  @Override
  public Optional<Message> handle(Message msg) {
    logger.info("new message:{}", msg);
    try {
      String error = Serializers.deserialize(msg.getPayload(), String.class);
      UUID sourceMessageId = msg.getSourceMessageId().orElseThrow(() -> new RuntimeException("Not found sourceMsg for message:" + msg.getId()));
      frontendService.takeConsumer(sourceMessageId, String.class).ifPresent(consumer -> consumer.accept(error));

    } catch (Exception ex) {
      logger.error("msg:" + msg, ex);
    }
    return Optional.empty();
  }
}
