package ru.otus.hw14;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw14.atm.*;
import ru.otus.hw14.department.ATMDepartment;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AtmDepartmentTest {
  private ATMDepartment atmDepartment;
  private ATM myAtm1;
  private ATM myAtm2;
  private ATM myAtm3;
  private ATM myAtm4;

  Map<Coin, Long> atmFullCompare = new HashMap<>();
  BigDecimal atmDefaultBalance = new BigDecimal(0);

  @BeforeEach
  public void createATM() {
    atmDepartment = new ATMDepartment();

    EnumSet.allOf(NominalsUSD.class).forEach(nominal -> atmFullCompare.put(new CoinUSD(nominal), 1000L));

    EnumSet.allOf(NominalsUSD.class).forEach(nominal -> {
      atmDefaultBalance.add(new BigDecimal(1000L).multiply(nominal.getValue()));
    });

    myAtm1 = new ATM("Pushkina st");
    myAtm2 = new ATM("Kolotushkina st");
    myAtm3 = new ATM("Biberevo st");
    myAtm4 = new ATM("Balashiha st");

    atmDepartment.addATM(myAtm1);
    atmDepartment.addATM(myAtm2);
    atmDepartment.addATM(myAtm3);
    atmDepartment.addATM(myAtm4);
  }

  @Test
  public void printAllATMBalance() throws ATMException {
    System.out.println("Before operations:");
    atmDepartment.printAllATMBalance();

    someRandomOperations();

    System.out.println("After operations:");
    atmDepartment.printAllATMBalance();
  }

  @Test
  public void restoreAllATMBalance() throws ATMException {
    System.out.println("\n\nBefore operations:");
    atmDepartment.printAllATMBalance();

    someRandomOperations();

    System.out.println("\n\nAfter operations:");
    atmDepartment.printAllATMBalance();

    atmDepartment.restoreAllATMBalance();

    System.out.println("\n\nAfter restore:");
    atmDepartment.printAllATMBalance();
    assertEquals(atmFullCompare, myAtm1.getBalanceMap());
  }

  private void someRandomOperations() throws ATMException {
    myAtm1.withdrawMoney(new BigDecimal(32352.21));
    myAtm1.withdrawMoney(new BigDecimal(123));

    BigDecimal myAtm1Total = new BigDecimal(0);
    // TODO: можно в метод ATM вынести
    EnumSet.allOf(NominalsUSD.class).forEach(nominal -> {
      myAtm1Total.add(new BigDecimal(myAtm1.getBalanceMap().get(new CoinUSD(nominal))).multiply(nominal.getValue()));
    });

    assertEquals(atmDefaultBalance.subtract(new BigDecimal(32352.21 + 123)), myAtm1Total);

    myAtm2.withdrawMoney(new BigDecimal(1233));
    myAtm2.withdrawMoney(new BigDecimal(552.11));

    BigDecimal myAtm2Total = new BigDecimal(0);
    EnumSet.allOf(NominalsUSD.class).forEach(nominal -> {
      myAtm2Total.add(new BigDecimal(myAtm2.getBalanceMap().get(new CoinUSD(nominal))).multiply(nominal.getValue()));
    });

    assertEquals(atmDefaultBalance.subtract(new BigDecimal(1233 + 552.11)), myAtm1Total);

    myAtm3.withdrawMoney(new BigDecimal(1203));
    myAtm3.withdrawMoney(new BigDecimal(51.55));

    BigDecimal myAtm3Total = new BigDecimal(0);
    EnumSet.allOf(NominalsUSD.class).forEach(nominal -> {
      myAtm3Total.add(new BigDecimal(myAtm3.getBalanceMap().get(new CoinUSD(nominal))).multiply(nominal.getValue()));
    });

    assertEquals(atmDefaultBalance.subtract(new BigDecimal(1203 + 51.55)), myAtm1Total);

    myAtm4.withdrawMoney(new BigDecimal(244));
    myAtm4.withdrawMoney(new BigDecimal(425.23));

    BigDecimal myAtm4Total = new BigDecimal(0);
    EnumSet.allOf(NominalsUSD.class).forEach(nominal -> {
      myAtm1Total.add(new BigDecimal(myAtm4.getBalanceMap().get(new CoinUSD(nominal))).multiply(nominal.getValue()));
    });

    assertEquals(atmDefaultBalance.subtract(new BigDecimal(244 + 425.23)), myAtm1Total);
  }
}
