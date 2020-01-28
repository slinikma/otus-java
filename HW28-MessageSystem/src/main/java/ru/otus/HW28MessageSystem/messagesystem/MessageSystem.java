package ru.otus.HW28MessageSystem.messagesystem;

import org.springframework.stereotype.Component;

public interface MessageSystem {

  void addClient(MsClient msClient);

  void removeClient(String clientId);

  boolean newMessage(Message msg);

  void dispose() throws InterruptedException;
}

