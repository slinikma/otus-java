package ru.otus.hw24.repository;

import com.mongodb.client.model.Filters;
import com.mongodb.reactivestreams.client.*;
import org.bson.Document;
import org.springframework.stereotype.Repository;
import ru.otus.hw24.domain.User;

import java.util.ArrayList;
import java.util.List;

// MongoUserDao -> MongoUserRepository
// TODO: можно DAO добавить как поле
@Repository
public class MongoUserRepository implements UserRepository {

  private final MongoClient mongoClient;
  private final MongoDatabase database;
  private MongoCollection<Document> collection;

  public MongoUserRepository() {
    mongoClient = MongoClients.create("mongodb://localhost");
    database = mongoClient.getDatabase("users");
    collection = database.getCollection("authenticationData");

    // Записываем дэфольного пользователя admin admin
    if (this.getDocumentByLogin("admin") == null) {
      var doc = new Document("login", "admin")
          .append("password", "admin");
      this.putDocumentToDB(doc);
    }
  }

  @Override
  public void save(String login, String password) {
    if (this.getDocumentByLogin(login) == null) {
      var doc = new Document("login", login)
          .append("password", password);

      this.putDocumentToDB(doc);
    }
  }

  @Override
  public User findByLogin(String login) {
    Document document = this.getDocumentByLogin(login);
    if (document != null) {
      User user = new User(document.get("login").toString(), document.get("password").toString());
      return user;
    } else {
      return null;
    }
  }

  @Override
  public List<User> getAllUsers() {
    var subscriber = new SubscriberHelpers.ObservableSubscriber<Document>();
    collection.find().subscribe(subscriber);
    try {
      subscriber.await();
    } catch (Throwable throwable) {
      throwable.printStackTrace();
    }

    List<User> users = new ArrayList<>();
    var results = subscriber.getResults();
    if (!results.isEmpty()) {
      results.iterator().forEachRemaining(document -> {
        users.add(new User(document.get("login").toString(), document.get("password").toString()));
      });
    }
    return users;
  }

  private Document getDocumentByLogin(String login) {
    var subscriber = new SubscriberHelpers.ObservableSubscriber<Document>();
    collection.find(Filters.eq("login", login)).subscribe(subscriber);
    try {
      subscriber.await();
    } catch (Throwable throwable) {
      throwable.printStackTrace();
    }

    var results = subscriber.getResults();
    if (!results.isEmpty()) {
      return subscriber.getResults().iterator().next();
    } else {
      return null;
    }
  }

  private void putDocumentToDB(Document doc) {
    var subscriber = new SubscriberHelpers.ObservableSubscriber<Success>();
    collection.insertOne(doc).subscribe(subscriber);
    try {
      subscriber.await();
    } catch (Throwable throwable) {
      throwable.printStackTrace();
    }
  }
}
