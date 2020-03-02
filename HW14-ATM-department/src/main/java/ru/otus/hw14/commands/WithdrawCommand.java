package ru.otus.hw14.commands;

import lombok.AllArgsConstructor;
import ru.otus.hw14.atm.ATM;
import ru.otus.hw14.atm.ATMException;

import java.math.BigDecimal;

@AllArgsConstructor
public class WithdrawCommand implements Command {

  private final BigDecimal cash;

  @Override
  public void execute(ATM atm) throws ATMException {
    atm.withdrawMoney(this.cash);
  }
}
