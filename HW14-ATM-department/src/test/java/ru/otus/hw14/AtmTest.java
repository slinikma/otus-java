package ru.otus.hw14;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw14.atm.*;

import java.math.BigDecimal;
import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AtmTest {
  private ATM myAtm;

  BigDecimal atmDefaultBalance = new BigDecimal(0);

  @BeforeEach
  public void createATM() {
    myAtm = new ATM("Pushkina st.");

    EnumSet.allOf(NominalsUSD.class).forEach(nominal -> {
      atmDefaultBalance.add(new BigDecimal(1000L).multiply(nominal.getValue()));
    });
  }

  @Test
  public void withdrawMoney() throws ATMException {
    myAtm.printATMBalance();

    myAtm.withdrawMoney(BigDecimal.valueOf(1246));
    myAtm.withdrawMoney(BigDecimal.valueOf(0.45));
    myAtm.withdrawMoney(BigDecimal.valueOf(1));

    BigDecimal myAtmTotal = new BigDecimal(0);
    // TODO: можно в метод ATM вынести
    EnumSet.allOf(NominalsUSD.class).forEach(nominal -> {
      myAtmTotal.add(new BigDecimal(myAtm.getBalanceMap().get(new CoinUSD(nominal))).multiply(nominal.getValue()));
    });

    assertEquals(atmDefaultBalance.subtract(new BigDecimal(1246 + 0.45 + 1)), myAtmTotal);

    myAtm.printATMBalance();
  }

  @Test
  public void printBalance() {
    myAtm.printATMBalance();
  }

  @Test
  public void putMoney() {
    myAtm.printATMBalance();

    myAtm.replenish(NominalsUSD.DOLLAR, 10L);
    myAtm.replenish(NominalsUSD.PENNY, 15L);
    myAtm.replenish(NominalsUSD.HUNDRED_DOLLARS, 11L);
    myAtm.replenish(NominalsUSD.DIME, 2L);

    BigDecimal myAtmTotal = new BigDecimal(0);
    // TODO: можно в метод ATM вынести
    EnumSet.allOf(NominalsUSD.class).forEach(nominal -> {
      myAtmTotal.add(new BigDecimal(myAtm.getBalanceMap().get(new CoinUSD(nominal))).multiply(nominal.getValue()));
    });

    assertEquals(atmDefaultBalance.add(new BigDecimal(10 + 0.15 + 1100 + 0.20)), myAtmTotal);

    myAtm.printATMBalance();
  }

  @Test
  public void notEnoughMoneyError() throws ATMException {
    assertThrows(ATMException.class, () -> {
      myAtm.withdrawMoney(BigDecimal.valueOf(99999999999L));
    });
  }
}
