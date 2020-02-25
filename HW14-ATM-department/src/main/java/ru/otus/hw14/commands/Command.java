package ru.otus.hw14.commands;

import ru.otus.hw14.atm.ATM;

public interface Command {

  public abstract void execute(ATM atm);

  public abstract void undo();
}
