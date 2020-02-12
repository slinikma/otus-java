package ru.otus.HW28MessageSystem;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import ru.otus.HW28MessageSystem.front.CustomHandshakeHandler;
import ru.otus.HW28MessageSystem.messagesystem.*;

@Configuration
@PropertySource("classpath:application.properties")
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Value("${spring.frontend_service.name}")
  private String FRONTEND_SERVICE_CLIENT_NAME;
  @Value("${spring.database_service.name}")
  private String DATABASE_SERVICE_CLIENT_NAME;

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
  public MsClient frontendMsClient(MessageSystem messageSystem) {
    return new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME, messageSystem);
  }

  @Bean(name = "databaseMsClient")
  public MsClient databaseMsClient(MessageSystem messageSystem) {
    return new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME, messageSystem);
  }
}
