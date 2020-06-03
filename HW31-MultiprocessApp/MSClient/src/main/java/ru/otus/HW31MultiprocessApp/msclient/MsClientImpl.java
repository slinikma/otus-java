package ru.otus.HW31MultiprocessApp.msclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.HW31MultiprocessApp.msclient.common.Serializers;

import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


public class MsClientImpl implements MsClient {
  private static final Logger logger = LoggerFactory.getLogger(MsClientImpl.class);
  private static final ObjectMapper objectMapper = new ObjectMapper()
      .registerModule(new Jdk8Module());;

  private final String name;
  private final int port;
  private final String host;

//  MessageSystem messageSystem;
  private final Map<String, RequestHandler> handlers = new ConcurrentHashMap<>();


  // TODO: вместо MessageSystem messageSystem должен быть хост и порт куда слать сообщения
  public MsClientImpl(
      String name,
      String host,
      Integer port
  ) {
    this.name = name;
    this.host = host;
    this.port = port;
  }

  // Не может содержать 2 хэндлера с одинаковым MessageType
  // Т.е. MessageType определяет, какой хэндлер будет вызван
  @Override
  public MsClient addHandler(MessageType type, RequestHandler requestHandler) {
    this.handlers.put(type.getValue(), requestHandler);
    return this;
  }

  @Override
  public String getName() {
    return name;
  }

  // Абстракция для остановки MessageSystem
  @Override
  public boolean sendMessage(Message msg) {
    boolean result = true; //messageSystem.newMessage(msg);

    try {
      try (Socket clientSocket = new Socket(host, port)) {
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        logger.info("sending to MS server");
        out.println(objectMapper.writeValueAsString(msg));
        Message respMsg = objectMapper.readValue(in.readLine(), Message.class);
        logger.info("MS server response: {}", respMsg.toString());

//        logger.info("stop communication");
//        out.println("stop");
      }

    } catch (Exception ex) {
      logger.error("error", ex);
    }

//    if (!result) {
//      logger.warn("the last message was rejected: {}", msg);
//      throw new RuntimeException("the last message was rejected: " + msg);
//    }
    return true; //result;
  }

  @Override
  public void handle(Message msg) {
    logger.info("new message:{}", msg);
    try {
      // Выбор реквест хэндлера происходит на основании типа сообщения
      RequestHandler requestHandler = handlers.get(msg.getType());
      if (requestHandler != null) {
        requestHandler.handle(msg).ifPresent(this::sendMessage);
      } else {
        logger.error("handler not found for the message type:{}", msg.getType());
      }
    } catch (Exception ex) {
      logger.error("msg:" + msg, ex);
    }
  }

  @Override
  public <T> Message produceMessage(String to, T data, MessageType msgType) {
    return new Message(name, to, null, msgType, Serializers.serialize(data));
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MsClientImpl msClient = (MsClientImpl) o;
    return Objects.equals(name, msClient.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
