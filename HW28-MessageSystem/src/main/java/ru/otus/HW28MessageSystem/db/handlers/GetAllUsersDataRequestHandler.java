package ru.otus.HW28MessageSystem.db.handlers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.HW28MessageSystem.common.Serializers;
import ru.otus.HW28MessageSystem.db.DBService;
import ru.otus.HW28MessageSystem.domain.User;
import ru.otus.HW28MessageSystem.messagesystem.Message;
import ru.otus.HW28MessageSystem.messagesystem.MessageType;
import ru.otus.HW28MessageSystem.messagesystem.RequestHandler;

import java.util.List;

import java.util.Optional;

@Component("getAllUsersDataRequestHandler")
public class GetAllUsersDataRequestHandler implements RequestHandler {

  private final DBService dbService;

  @Autowired
  public GetAllUsersDataRequestHandler(DBService dbService) {
    this.dbService = dbService;
  }

  @Override
  public Optional<Message> handle(Message msg) {
    try {
      List<User> data = dbService.getAllUsers();
      return Optional.of(new Message(msg.getTo(), msg.getFrom(), Optional.of(msg.getId()), MessageType.USERS_LIST.getValue(), Serializers.serialize(data)));
    } catch(Exception e){
      return Optional.of(new Message(msg.getTo(), msg.getFrom(), Optional.of(msg.getId()), MessageType.ERRORS.getValue(), Serializers.serialize(e)));
    }
  }
}