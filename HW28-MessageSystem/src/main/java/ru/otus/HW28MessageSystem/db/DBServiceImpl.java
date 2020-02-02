package ru.otus.HW28MessageSystem.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.HW28MessageSystem.domain.User;
import ru.otus.HW28MessageSystem.repository.UserRepository;

import java.util.List;

@Service
public class DBServiceImpl implements DBService {
  private static final Logger logger = LoggerFactory.getLogger(DBServiceImpl.class);

  @Autowired
  UserRepository userRepository;

  public User saveUser(User user) {
    logger.info("saving user {} in DB", user);
    return userRepository.save(user);
  }


  public List<User> getAllUsers() {
    logger.info("getting all users from DB");
    return userRepository.findAll();
  }

}
