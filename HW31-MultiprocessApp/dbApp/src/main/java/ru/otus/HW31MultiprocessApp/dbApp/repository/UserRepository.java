package ru.otus.HW31MultiprocessApp.dbApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.HW31MultiprocessApp.msclient.domain.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
  public User findByLogin(String login);
}
