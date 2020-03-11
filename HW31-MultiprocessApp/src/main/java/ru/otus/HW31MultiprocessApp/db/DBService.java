package ru.otus.HW31MultiprocessApp.db;

import org.springframework.stereotype.Service;
import ru.otus.HW31MultiprocessApp.domain.User;

import java.util.List;

public interface DBService {
  User saveUser(User user);
  List<User> getAllUsers();
}
