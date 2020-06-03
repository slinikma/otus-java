package ru.otus.HW31MultiprocessApp.msclient;

public interface MsClient {

  MsClient addHandler(MessageType type, RequestHandler requestHandler);

  boolean sendMessage(Message msg);

  void handle(Message msg);

  String getName();

  <T> Message produceMessage(String to, T data, MessageType msgType);

}
