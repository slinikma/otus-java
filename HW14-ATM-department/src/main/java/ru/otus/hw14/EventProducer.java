package ru.otus.hw14;

import ru.otus.hw14.commands.Command;

import java.util.ArrayList;
import java.util.List;

public class EventProducer {

  private final List<Listener> listeners = new ArrayList<>();

  public void addListener(Listener listener) {
    listeners.add(listener);
  }

  public void removeListener(Listener listener) {
    listeners.remove(listener);
  }

  public void event(Command cmd) {
    listeners.forEach(listener -> listener.onUpdate(cmd));
  }
}
