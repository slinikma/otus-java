package ru.otus.HW31MultiprocessApp.dbApp.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.HW31MultiprocessApp.msclient.Message;
import ru.otus.HW31MultiprocessApp.msclient.MessageType;
import ru.otus.HW31MultiprocessApp.msclient.RequestHandler;
import ru.otus.HW31MultiprocessApp.msclient.common.Serializers;
import ru.otus.HW31MultiprocessApp.dbApp.DBService;
import ru.otus.HW31MultiprocessApp.msclient.domain.User;

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
      return Optional.of(new Message(msg.getTo(), msg.getFrom(), Optional.of(msg.getId()), MessageType.USER_DATA, Serializers.serialize(user)));
    } catch(Exception e){
      return Optional.of(new Message(msg.getTo(), msg.getFrom(), Optional.of(msg.getId()), MessageType.ERRORS, Serializers.serialize(e.getMessage())));
    }
  }
}
