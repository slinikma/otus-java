package ru.otus.HW31MultiprocessApp.frontApp;

import ru.otus.HW31MultiprocessApp.msclient.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

// API нашего серверного приложения
// Формирует сообщения для message system
public interface FrontendService {
  void getAllUsers(Consumer<List<User>> dataConsumer);
  void saveUser(User user, Consumer<User> dataConsumer, Consumer<String> errorConsumer);

  <T> Optional<Consumer<T>> takeConsumer(UUID sourceMessageId, Class<T> tClass);
}
