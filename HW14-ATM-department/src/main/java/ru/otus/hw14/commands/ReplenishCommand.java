package ru.otus.hw14.commands;

import lombok.AllArgsConstructor;
import ru.otus.hw14.atm.ATM;
import ru.otus.hw14.atm.Nominals;

@AllArgsConstructor
public class ReplenishCommand implements Command {

  private final Nominals nominal;
  private final Long amount;

  @Override
  public void execute(ATM atm) {
    atm.replenish(this.nominal, this.amount);
  }

  @Override
  public void undo() {
    // TODO: а вот тут нужен мементо, который бы хранил состояния всех карзин
  }
}
