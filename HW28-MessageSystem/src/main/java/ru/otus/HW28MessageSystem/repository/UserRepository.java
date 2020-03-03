package ru.otus.HW28MessageSystem.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.HW28MessageSystem.domain.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
  public User findByLogin(String login);
}
