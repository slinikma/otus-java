package ru.otus.HW31MultiprocessApp.msclient;


import java.util.Optional;

public interface RequestHandler {
  Optional<Message> handle(Message msg);
}
