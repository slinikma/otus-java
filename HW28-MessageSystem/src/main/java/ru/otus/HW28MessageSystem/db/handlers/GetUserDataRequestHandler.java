package ru.otus.HW28MessageSystem.db.handlers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import ru.otus.HW28MessageSystem.common.Serializers;
import ru.otus.HW28MessageSystem.db.DBService;
import ru.otus.HW28MessageSystem.messagesystem.Message;
import ru.otus.HW28MessageSystem.messagesystem.MessageType;
import ru.otus.HW28MessageSystem.messagesystem.RequestHandler;

import java.util.Optional;

//@Component("getUserDataRequestHandler")
@Configurable
public class GetUserDataRequestHandler implements RequestHandler {

  @Autowired
  DBService dbService;

//  public GetUserDataRequestHandler(DBService dbService) {
//    this.dbService = dbService;
//  }

  @Override
  public Optional<Message> handle(Message msg) {
    long id = Serializers.deserialize(msg.getPayload(), Long.class);
    String data = ""; // dbService.getUserData(id);
    return Optional.of(new Message(msg.getTo(), msg.getFrom(), Optional.of(msg.getId()), MessageType.USER_DATA.getValue(), Serializers.serialize(data)));
  }
}
