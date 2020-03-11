package ru.otus.HW31MultiprocessApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.HW31MultiprocessApp.domain.User;
import ru.otus.HW31MultiprocessApp.domain.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
  public User findByLogin(String login);
}
