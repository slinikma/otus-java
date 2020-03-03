package ru.otus.HW28MessageSystem.db;

import org.springframework.stereotype.Service;
import ru.otus.HW28MessageSystem.domain.User;

import java.util.List;

public interface DBService {
  User saveUser(User user);
  List<User> getAllUsers();
}
