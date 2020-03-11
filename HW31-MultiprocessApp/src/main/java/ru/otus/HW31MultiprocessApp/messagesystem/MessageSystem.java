package ru.otus.HW31MultiprocessApp.messagesystem;

public interface MessageSystem {

  void addClient(MsClient msClient);

  void removeClient(String clientId);

  boolean newMessage(Message msg);

  void dispose() throws InterruptedException;
}

