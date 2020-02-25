package ru.otus.hw14.commands;

import lombok.AllArgsConstructor;
import ru.otus.hw14.atm.ATM;

import java.math.BigDecimal;

@AllArgsConstructor
public class WithdrawCommand implements Command {

  private final BigDecimal cash;

  @Override
  public void execute(ATM atm) {
    atm.withdrawMoney(this.cash);
  }

  @Override
  public void undo() {
    // TODO: а вот тут нужен мементо, который бы хранил состояния всех карзин
  }
}
