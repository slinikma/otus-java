package ru.otus.HW31MultiprocessApp.messagesystem;

import ru.otus.HW31MultiprocessApp.msclient.Message;
import ru.otus.HW31MultiprocessApp.msclient.MsClient;

import java.net.Socket;

public interface MessageSystem {

  void addClient(String clientId, Socket msClient);

  void removeClient(String clientId);

  boolean newMessage(Message msg);

  void dispose() throws InterruptedException;

  void clientRequestHandler(Socket clientSocket);
}

