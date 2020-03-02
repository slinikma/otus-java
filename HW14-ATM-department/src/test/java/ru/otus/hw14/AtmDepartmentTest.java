package ru.otus.hw14;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw14.atm.*;
import ru.otus.hw14.department.ATMDepartment;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
      atmDefaultBalance = atmDefaultBalance.add(new BigDecimal(1000L).multiply(nominal.getValue())).setScale(2, RoundingMode.HALF_EVEN);
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

    assertEquals(atmDefaultBalance.subtract(new BigDecimal(32352.21 + 123)).setScale(2, RoundingMode.HALF_EVEN), myAtm1.getTotalCash());

    myAtm2.withdrawMoney(new BigDecimal(1233));
    myAtm2.withdrawMoney(new BigDecimal(552.11));

    assertEquals(atmDefaultBalance.subtract(new BigDecimal(1233 + 552.11)).setScale(2, RoundingMode.HALF_EVEN), myAtm2.getTotalCash());

    myAtm3.withdrawMoney(new BigDecimal(1203));
    myAtm3.withdrawMoney(new BigDecimal(51.55));

    assertEquals(atmDefaultBalance.subtract(new BigDecimal(1203 + 51.55)).setScale(2, RoundingMode.HALF_EVEN), myAtm3.getTotalCash());

    myAtm4.withdrawMoney(new BigDecimal(244));
    myAtm4.withdrawMoney(new BigDecimal(425.23));

    assertEquals(atmDefaultBalance.subtract(new BigDecimal(244 + 425.23)).setScale(2, RoundingMode.HALF_EVEN), myAtm4.getTotalCash());
  }
}
