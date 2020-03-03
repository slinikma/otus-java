package ru.otus.HW28MessageSystem.messagesystem;

public interface MessageSystem {

  void addClient(MsClient msClient);

  void removeClient(String clientId);

  boolean newMessage(Message msg);

  void dispose() throws InterruptedException;
}

