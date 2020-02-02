package ru.otus.HW28MessageSystem.front;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.HW28MessageSystem.domain.User;
import ru.otus.HW28MessageSystem.messagesystem.Message;
import ru.otus.HW28MessageSystem.messagesystem.MessageType;
import ru.otus.HW28MessageSystem.messagesystem.MsClient;


import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

// Не можем сделать бином, т.к. MsClient не бин, а databaseServiceClientName хотим передать в конструктор
public class FrontendServiceImpl implements FrontendService {
  private static final Logger logger = LoggerFactory.getLogger(FrontendServiceImpl.class);

  private final Map<UUID, Consumer<?>> consumerMap = new ConcurrentHashMap<>();
  private final MsClient msClient;
  // Имя получателя
  private final String databaseServiceClientName;

  public FrontendServiceImpl(MsClient msClient, String databaseServiceClientName) {
    this.msClient = msClient;
    this.databaseServiceClientName = databaseServiceClientName;
  }

  @Override
  public void getAllUsers(Consumer<List<User>> dataConsumer) {
    // Сообщение предназначено для сервиса работы с базой данных и вызывает метод получения всех пользователей
    // Сервис отвечает на сообщение с типом USERS_LIST, тело сообщения не важно в данном случае (как GET запрос)
    Message outMsg = msClient.produceMessage(databaseServiceClientName, "", MessageType.USERS_LIST);
    consumerMap.put(outMsg.getId(), dataConsumer);
    // Отправляем сообщение в очередь
    msClient.sendMessage(outMsg);
  }

  @Override
  public void saveUser(User user, Consumer<User> dataConsumer) {
    // Сообщение предназначено для сервиса работы с базой данных и вызывает метод создания пользователя
    // Сервис отвечает на сообщение с типом USER_DATA
    Message outMsg = msClient.produceMessage(databaseServiceClientName, user, MessageType.USER_DATA);
    // Мапа клбэк сервисов и id сообщений
    consumerMap.put(outMsg.getId(), dataConsumer);
    // Отправляем сообщение в мапу с сервисами
    msClient.sendMessage(outMsg);
  }

  @Override
  public <T> Optional<Consumer<T>> takeConsumer(UUID sourceMessageId, Class<T> tClass) {
    Consumer<T> consumer = (Consumer<T>) consumerMap.remove(sourceMessageId);
    if (consumer == null) {
      logger.warn("consumer not found for:{}", sourceMessageId);
      return Optional.empty();
    }
    return Optional.of(consumer);
  }
}
