package ru.otus.hw07;

import org.junit.jupiter.api.BeforeAll;
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
    myAtm.withdrawMoney(BigDecimal.valueOf(1000));
  }

  @Test
  public void printBinStat() { myAtm.pringBinStat(); }

  @Test
  public void printBalance() { myAtm.printBalance(); }
}
