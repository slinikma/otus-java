package ru.otus.HW31MultiprocessApp.dbApp;

import ru.otus.HW31MultiprocessApp.msclient.domain.User;

import java.net.Socket;
import java.util.List;

public interface DBService {
  User saveUser(User user);
  List<User> getAllUsers();
  void clientHandler(Socket clientSocket);
}
