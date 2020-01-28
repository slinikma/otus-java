package ru.otus.HW28MessageSystem.db;

import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.HW28MessageSystem.domain.User;
import ru.otus.HW28MessageSystem.repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DBServiceImpl implements DBService {
  private static final Logger logger = LoggerFactory.getLogger(DBServiceImpl.class);

  @Autowired
  UserRepository userRepository;

//  private final Map<Long, String> database = new HashMap<>();

//  private void initDatabase() {
//    database.put(1L, "val1");
//    database.put(2L, "val2");
//    database.put(3L, "val3");
//  }

//  public DBServiceImpl() {
//    initDatabase();
//  }



//  public String getUserData(long id) {
//    logger.info("get data for id:{}", id);
//    return database.get(id);
//  }


  public List<User> getAllUsers() {
    logger.info("getting all users from DB");
    return userRepository.findAll();
  }

}
