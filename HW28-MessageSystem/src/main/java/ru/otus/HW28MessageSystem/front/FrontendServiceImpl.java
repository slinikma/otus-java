package ru.otus.HW28MessageSystem.front;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.otus.HW28MessageSystem.domain.User;
import ru.otus.HW28MessageSystem.messagesystem.Message;
import ru.otus.HW28MessageSystem.messagesystem.MessageType;
import ru.otus.HW28MessageSystem.messagesystem.MsClient;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;


@Service
public class FrontendServiceImpl implements FrontendService {
  private static final Logger logger = LoggerFactory.getLogger(FrontendServiceImpl.class);

  private final Map<CompositeKey, Consumer<?>> consumerMap = new ConcurrentHashMap<>();
  private Consumer<String> errorConsumer;
  private final MsClient frontendMsClient;
  // Имя получателя
  @Value("${spring.database_service.name}")
  private String databaseServiceClientName;

  @Autowired
  public FrontendServiceImpl(@Lazy MsClient frontendMsClient) {
    this.frontendMsClient = frontendMsClient;
  }

  @Override
  public void getAllUsers(Consumer<List<User>> dataConsumer) {
    // Сообщение предназначено для сервиса работы с базой данных и вызывает метод получения всех пользователей
    // Сервис отвечает на сообщение с типом USERS_LIST, тело сообщения не важно в данном случае (как GET запрос)
    Message outMsg = frontendMsClient.produceMessage(databaseServiceClientName, "", MessageType.USERS_LIST);
    consumerMap.put(new CompositeKey(outMsg.getId(), ArrayList.class), dataConsumer);
    // Отправляем сообщение в очередь
    frontendMsClient.sendMessage(outMsg);
  }

  @Override
  public void saveUser(User user, Consumer<User> dataConsumer, Consumer<String> errorConsumer) {
    // Сообщение предназначено для сервиса работы с базой данных и вызывает метод создания пользователя
    // Сервис отвечает на сообщение с типом USER_DATA
    Message outMsg = frontendMsClient.produceMessage(databaseServiceClientName, user, MessageType.USER_DATA);
    // Мапа клбэк сервисов и id сообщений
    consumerMap.put(new CompositeKey(outMsg.getId(), User.class), dataConsumer);
    consumerMap.put(new CompositeKey(outMsg.getId(), String.class), errorConsumer);
    // Отправляем сообщение в мапу с сервисами
    frontendMsClient.sendMessage(outMsg);
  }

  @Override
  public <T> Optional<Consumer<T>> takeConsumer(UUID sourceMessageId, Class<T> tClass) {
    // Как-то кривенько ...
    Consumer<T> consumer = (Consumer<T>) consumerMap.remove(new CompositeKey(sourceMessageId, tClass));
    if (consumer == null) {
      logger.warn("consumer not found for:{}", sourceMessageId);
      return Optional.empty();
    }
    return Optional.of(consumer);
  }


  @EqualsAndHashCode
  @AllArgsConstructor
  private class CompositeKey<T> {
    UUID sourceMessageId;
    Class tClass;
  }
}
