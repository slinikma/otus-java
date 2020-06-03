package ru.otus.HW31MultiprocessApp.messagesystem;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import ru.otus.HW31MultiprocessApp.msclient.MessageType;
import ru.otus.HW31MultiprocessApp.msclient.MsClient;
import ru.otus.HW31MultiprocessApp.msclient.MsClientImpl;
import ru.otus.HW31MultiprocessApp.msclient.RequestHandler;

import java.net.Socket;

@Configuration
@PropertySource("classpath:application.properties")
public class MessageSystemConfig {

  private String fronted_service_client_name;
  private String database_service_client_name;

  public MessageSystemConfig (
      @Value("${spring.frontend_service.name}") final String fronted_service_client_name,
      @Value("${spring.database_service.name}") final String database_service_client_name
  ) {
    this.fronted_service_client_name = fronted_service_client_name;
    this.database_service_client_name = database_service_client_name;
  }

//  @Lazy
//  @Bean(name = "frontendMsClient")
//  public MsClient frontendMsClient(RequestHandler createUserResponseHandler,
//                                   RequestHandler getAllUsersDataResponseHandler,
//                                   RequestHandler errorHandler)
//  {
//    // TODO: host, port d в проперти
//    return new MsClientImpl(fronted_service_client_name, "localhost", 8081)
//        .addHandler(MessageType.USER_DATA, createUserResponseHandler)
//        .addHandler(MessageType.USERS_LIST, getAllUsersDataResponseHandler)
//        .addHandler(MessageType.ERRORS, errorHandler);
//  }
//
//  @Bean(name = "databaseMsClient")
//  public MsClient databaseMsClient(RequestHandler createUserRequestHandler,
//                                   RequestHandler getAllUsersDataRequestHandler)
//  {
//    return new MsClientImpl(database_service_client_name, "localhost", 8082)
//        .addHandler(MessageType.USER_DATA, createUserRequestHandler)
//        .addHandler(MessageType.USERS_LIST, getAllUsersDataRequestHandler);
//  }

  @SneakyThrows
  @Autowired
  public void setupMessageSystem(MessageSystem messageSystem) {
    messageSystem.addClient("databaseMsClient", new Socket("localhost", 8082));
//    messageSystem.addClient(frontendMsClient);
    // TODO: подумать, как добавлять БД. И добавить addClient при запросе!!
  }
}
