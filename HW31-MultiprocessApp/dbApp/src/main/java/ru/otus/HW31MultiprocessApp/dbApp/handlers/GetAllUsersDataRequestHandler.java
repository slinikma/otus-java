package ru.otus.HW31MultiprocessApp.dbApp.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.HW31MultiprocessApp.msclient.Message;
import ru.otus.HW31MultiprocessApp.msclient.MessageType;
import ru.otus.HW31MultiprocessApp.msclient.RequestHandler;
import ru.otus.HW31MultiprocessApp.msclient.common.Serializers;
import ru.otus.HW31MultiprocessApp.dbApp.DBService;
import ru.otus.HW31MultiprocessApp.msclient.domain.User;

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
      return Optional.of(new Message(msg.getTo(), msg.getFrom(), Optional.of(msg.getId()), MessageType.USERS_LIST, Serializers.serialize(data)));
    } catch(Exception e){
      return Optional.of(new Message(msg.getTo(), msg.getFrom(), Optional.of(msg.getId()), MessageType.ERRORS, Serializers.serialize(e)));
    }
  }
}
