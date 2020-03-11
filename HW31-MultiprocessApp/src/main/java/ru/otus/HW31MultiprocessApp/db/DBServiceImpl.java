package ru.otus.HW31MultiprocessApp.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.HW31MultiprocessApp.domain.User;
import ru.otus.HW31MultiprocessApp.repository.UserRepository;

import java.util.List;

@Service
public class DBServiceImpl implements DBService {
  private static final Logger logger = LoggerFactory.getLogger(DBServiceImpl.class);

  private final UserRepository userRepository;

  @Autowired
  public DBServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User saveUser(User user) {
    logger.info("saving user {} in DB", user);
    return userRepository.save(user);
  }


  public List<User> getAllUsers() {
    logger.info("getting all users from DB");
    return userRepository.findAll();
  }

}
