package ru.otus.hw14.commands;

import ru.otus.hw14.atm.ATM;

public class PrintBalanceCommand implements Command {

  @Override
  public void execute(ATM atm) {
    atm.printATMBalance();
  }
}
