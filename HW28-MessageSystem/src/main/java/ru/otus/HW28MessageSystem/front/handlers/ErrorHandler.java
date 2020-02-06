package ru.otus.HW28MessageSystem.front.handlers;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.HW28MessageSystem.common.Serializers;
import ru.otus.HW28MessageSystem.front.FrontendService;
import ru.otus.HW28MessageSystem.messagesystem.Message;
import ru.otus.HW28MessageSystem.messagesystem.RequestHandler;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@AllArgsConstructor
public class ErrorHandler implements RequestHandler {

  private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

  private FrontendService frontendService;

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
