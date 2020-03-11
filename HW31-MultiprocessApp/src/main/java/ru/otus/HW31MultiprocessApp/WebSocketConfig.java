package ru.otus.HW31MultiprocessApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import ru.otus.HW31MultiprocessApp.front.CustomHandshakeHandler;
import ru.otus.HW31MultiprocessApp.messagesystem.*;
import ru.otus.HW31MultiprocessApp.messagesystem.MessageSystem;
import ru.otus.HW31MultiprocessApp.messagesystem.MessageType;
import ru.otus.HW31MultiprocessApp.messagesystem.MsClient;
import ru.otus.HW31MultiprocessApp.messagesystem.RequestHandler;

@Configuration
@PropertySource("classpath:application.properties")
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  private String fronted_service_client_name;
  private String database_service_client_name;

  public WebSocketConfig (
      @Value("${spring.frontend_service.name}") final String fronted_service_client_name,
      @Value("${spring.database_service.name}") final String database_service_client_name
  ) {
    this.fronted_service_client_name = fronted_service_client_name;
    this.database_service_client_name = database_service_client_name;
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/topic");
    config.setApplicationDestinationPrefixes("/app");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/gs-guide-websocket")
        .setHandshakeHandler(new CustomHandshakeHandler())
        .withSockJS();
  }

  @Lazy
  @Bean(name = "frontendMsClient")
  public MsClient frontendMsClient(MessageSystem messageSystem,
                                   RequestHandler createUserResponseHandler,
                                   RequestHandler getAllUsersDataResponseHandler,
                                   RequestHandler errorHandler)
  {
    return new MsClientImpl(fronted_service_client_name, messageSystem)
        .addHandler(MessageType.USER_DATA, createUserResponseHandler)
        .addHandler(MessageType.USERS_LIST, getAllUsersDataResponseHandler)
        .addHandler(MessageType.ERRORS, errorHandler);
  }

  @Bean(name = "databaseMsClient")
  public MsClient databaseMsClient(MessageSystem messageSystem,
                                   RequestHandler createUserRequestHandler,
                                   RequestHandler getAllUsersDataRequestHandler)
  {
    return new MsClientImpl(database_service_client_name, messageSystem)
        .addHandler(MessageType.USER_DATA, createUserRequestHandler)
        .addHandler(MessageType.USERS_LIST, getAllUsersDataRequestHandler);
  }

  @Autowired
  public void setupMessageSystem(MessageSystem messageSystem,
                                 MsClient databaseMsClient,
                                 MsClient frontendMsClient) {
    messageSystem.addClient(databaseMsClient);
    messageSystem.addClient(frontendMsClient);
  }
}
