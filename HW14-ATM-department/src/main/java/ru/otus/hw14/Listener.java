package ru.otus.hw14;

import ru.otus.hw14.commands.Command;

public interface Listener {
  void onUpdate(Command command);
}
