package ru.otus.HW31MultiprocessApp.dbApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.HW31MultiprocessApp.msclient.Message;
import ru.otus.HW31MultiprocessApp.msclient.MessageType;
import ru.otus.HW31MultiprocessApp.msclient.common.Serializers;
import ru.otus.HW31MultiprocessApp.msclient.domain.User;
import ru.otus.HW31MultiprocessApp.dbApp.repository.UserRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

@Service
public class DBServiceImpl implements DBService {
  private static final Logger logger = LoggerFactory.getLogger(DBServiceImpl.class);
  private static final int PORT = 8082; // TODO: move to property
  private static final ObjectMapper objectMapper = new ObjectMapper()
      .registerModule(new Jdk8Module());

  private final UserRepository userRepository;

  @Autowired
  public DBServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
    this.doSomethingAfterStartup();
  }

  @Override
  public User saveUser(User user) {
    logger.info("saving user {} in DB", user);
    return userRepository.save(user);
  }

  @Override
  public List<User> getAllUsers() {
    logger.info("getting all users from DB");
    return userRepository.findAll();
  }

  @Override
  // TODO: тут ли доджен быть??
  public void clientHandler(Socket clientSocket) {
    try (
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
    ) {
      Message msg = objectMapper.readValue(in.readLine(), Message.class);
      logger.info("from client: {} ", msg.toString());
      // TODO: Типы в АПИ?
      switch (msg.getType()) {
        case USER_DATA:
          var savedUser = saveUser(Serializers.deserialize(msg.getPayload(), User.class));
          break;
        case USERS_LIST:
          var users = getAllUsers();
          break;
        case ERRORS:
          break;
        case VOID:
          break;
      }

      out.println(objectMapper.writeValueAsString(Message.VOID_MESSAGE));

//      while (!"stop".equals(input)) {
//        if (input != null) {
////          out.println("echo:" + input);
//        }
//      }
      // insertStopMessage()
    } catch (Exception ex) {
      logger.error("error", ex);
    }
  }

  public void doSomethingAfterStartup() {
    //DatagramSocket - UDP
    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
      while (!Thread.currentThread().isInterrupted()) {
        logger.info("waiting for client connection");
        try (Socket clientSocket = serverSocket.accept()) {
          this.clientHandler(clientSocket);
        }
      }
    } catch (Exception ex) {
      logger.error("error", ex);
    }
  }

}
