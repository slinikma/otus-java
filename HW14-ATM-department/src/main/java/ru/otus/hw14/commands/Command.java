package ru.otus.hw14.commands;

import ru.otus.hw14.atm.ATM;
import ru.otus.hw14.atm.ATMException;

public interface Command {
  public abstract void execute(ATM atm) throws ATMException;
}
