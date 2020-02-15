package ru.otus.hw07;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class AtmTest {
  private ATM myAtm;

  @BeforeEach
  public void createATM() {
    myAtm = new ATM();
  }

  @Test
  public void withdrawMoney() {
    myAtm.withdrawMoney(BigDecimal.valueOf(1246));
  }

  @Test
  public void printBalance() {
    myAtm.printATMBalance();
    myAtm.withdrawMoney(BigDecimal.valueOf(124542));
    myAtm.withdrawMoney(BigDecimal.valueOf(2.23));
    myAtm.printATMBalance();
  }

  @Test
  public void putMoney() {
    myAtm.printATMBalance();
    myAtm.replenish(NominalsUSD.DOLLAR, 10L);
    myAtm.replenish(NominalsUSD.PENNY, 15L);
    myAtm.replenish(NominalsUSD.HUNDRED_DOLLARS, 11L);
    myAtm.printATMBalance();
  }

  @Test
  public void notEnoughMoneyError() {
    myAtm.withdrawMoney(BigDecimal.valueOf(99999999999L));
  }
}
