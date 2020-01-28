package ru.otus.HW28MessageSystem.db.handlers;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.otus.HW28MessageSystem.common.Serializers;
import ru.otus.HW28MessageSystem.db.DBService;
import ru.otus.HW28MessageSystem.domain.User;
import ru.otus.HW28MessageSystem.messagesystem.Message;
import ru.otus.HW28MessageSystem.messagesystem.MessageType;
import ru.otus.HW28MessageSystem.messagesystem.RequestHandler;

import javax.annotation.PostConstruct;
import java.util.List;

import java.util.Optional;

// TODO: сделать получение всех пользаков
//@Component("getAllUsersDataRequestHandler")
@Service
@Configurable
public class GetAllUsersDataRequestHandler implements RequestHandler {

  @Autowired
  DBService dbService;

//  @PostConstruct
//  public void init() {
//    System.out.println("test: " + dbService);
//  }

  @Override
  public Optional<Message> handle(Message msg) {
//    long id = Serializers.deserialize(msg.getPayload(), Long.class);
    List<User> data = dbService.getAllUsers();
    return Optional.of(new Message(msg.getTo(), msg.getFrom(), Optional.of(msg.getId()), MessageType.USER_DATA.getValue(), Serializers.serialize(data)));
  }
}
