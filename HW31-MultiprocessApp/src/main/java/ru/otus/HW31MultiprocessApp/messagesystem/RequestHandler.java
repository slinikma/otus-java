package ru.otus.HW31MultiprocessApp.messagesystem;


import java.util.Optional;

public interface RequestHandler {
  Optional<Message> handle(Message msg);
}
