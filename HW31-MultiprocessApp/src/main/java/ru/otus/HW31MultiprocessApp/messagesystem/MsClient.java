package ru.otus.HW31MultiprocessApp.messagesystem;

public interface MsClient {

  MsClient addHandler(MessageType type, RequestHandler requestHandler);

  boolean sendMessage(Message msg);

  void handle(Message msg);

  String getName();

  <T> Message produceMessage(String to, T data, MessageType msgType);

}
