package ru.otus.HW28MessageSystem.front;

import ru.otus.HW28MessageSystem.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

// API нашего серверного приложения
// Формирует сообщения для message system
public interface FrontendService {
  void getUserData(long userId, Consumer<String> dataConsumer);
  void getAllUsers(Consumer<List<User>> dataConsumer);

  <T> Optional<Consumer<T>> takeConsumer(UUID sourceMessageId, Class<T> tClass);
}
