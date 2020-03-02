package ru.otus.hw14.commands;

import lombok.AllArgsConstructor;
import ru.otus.hw14.atm.ATM;

@AllArgsConstructor
public class RestoreBalanceCommand implements Command {

  @Override
  public void execute(ATM atm) {
    while (atm.restorePreviousState()) {
      System.out.println("[ATM at " + atm.getAtmAddress() + "] Previous state was restored!");
      atm.printATMBalance();
    }
  }
}
