package ru.otus.HW28MessageSystem.front;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

// TODO: т.к. есть конструктор в который я задаю параметры, это не сервис
// TODO: или же использовать Autowired над конструктором с параметрами?
// TODO: https://docs.spring.io/spring/docs/3.0.x/javadoc-api/index.html?org/springframework/beans/factory/annotation/Value.html
//@Service
public class FrontendServiceImpl implements FrontendService {
  private static final Logger logger = LoggerFactory.getLogger(FrontendServiceImpl.class);

  private final Map<UUID, Consumer<?>> consumerMap = new ConcurrentHashMap<>();
  private final MsClient msClient;
  // Имя получателя
  private final String databaseServiceClientName;

//  @Autowired
  public FrontendServiceImpl(MsClient msClient, String databaseServiceClientName) {
    this.msClient = msClient;
    this.databaseServiceClientName = databaseServiceClientName;
  }

  @Override
  public void getUserData(long userId, Consumer<String> dataConsumer) {
    Message outMsg = msClient.produceMessage(databaseServiceClientName, userId, MessageType.USER_DATA);
    consumerMap.put(outMsg.getId(), dataConsumer);
    msClient.sendMessage(outMsg);
  }

  @Override
  public void getAllUsers(Consumer<List<User>> dataConsumer) {
    // Отправляем сообщение сервису базы данных, с сообщением *, типа USER_DATA
    // TODO: USER_DATA определяет сервис? или по имени?
    // TODO: видимо нужны отдельные сервисы? (нет)
    // Сообщение предназначено для сервиса работы с базой данных и вызывает метод получения всех пользователей
    Message outMsg = msClient.produceMessage(databaseServiceClientName, "", MessageType.USERS_LIST);
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
