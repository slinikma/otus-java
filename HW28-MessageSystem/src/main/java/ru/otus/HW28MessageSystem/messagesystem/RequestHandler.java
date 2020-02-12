package ru.otus.HW28MessageSystem.messagesystem;


import java.util.Optional;

public interface RequestHandler {
  Optional<Message> handle(Message msg);
}
