package ru.otus.HW31MultiprocessApp.db.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.HW31MultiprocessApp.common.Serializers;
import ru.otus.HW31MultiprocessApp.db.DBService;
import ru.otus.HW31MultiprocessApp.domain.User;
import ru.otus.HW31MultiprocessApp.messagesystem.Message;
import ru.otus.HW31MultiprocessApp.messagesystem.MessageType;
import ru.otus.HW31MultiprocessApp.messagesystem.RequestHandler;
import ru.otus.HW31MultiprocessApp.messagesystem.Message;
import ru.otus.HW31MultiprocessApp.messagesystem.MessageType;
import ru.otus.HW31MultiprocessApp.messagesystem.RequestHandler;

import java.util.Optional;

@Component("createUserRequestHandler")
public class CreateUserRequestHandler implements RequestHandler {

  private final DBService dbService;

  @Autowired
  public CreateUserRequestHandler(DBService dbService) {
    this.dbService = dbService;
  }

  @Override
  public Optional<Message> handle(Message msg) {
    try {
      User user = dbService.saveUser(Serializers.deserialize(msg.getPayload(), User.class));
      return Optional.of(new Message(msg.getTo(), msg.getFrom(), Optional.of(msg.getId()), MessageType.USER_DATA.getValue(), Serializers.serialize(user)));
    } catch(Exception e){
      return Optional.of(new Message(msg.getTo(), msg.getFrom(), Optional.of(msg.getId()), MessageType.ERRORS.getValue(), Serializers.serialize(e.getMessage())));
    }
  }
}
