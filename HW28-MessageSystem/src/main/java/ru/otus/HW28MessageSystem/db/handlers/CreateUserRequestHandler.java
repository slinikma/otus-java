package ru.otus.HW28MessageSystem.db.handlers;

import lombok.AllArgsConstructor;
import ru.otus.HW28MessageSystem.common.Serializers;
import ru.otus.HW28MessageSystem.db.DBService;
import ru.otus.HW28MessageSystem.domain.User;
import ru.otus.HW28MessageSystem.messagesystem.Message;
import ru.otus.HW28MessageSystem.messagesystem.MessageType;
import ru.otus.HW28MessageSystem.messagesystem.RequestHandler;

import java.util.Optional;

@AllArgsConstructor
public class CreateUserRequestHandler implements RequestHandler {

  DBService dbService;

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
