package ru.otus.hw14;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw14.atm.ATM;
import ru.otus.hw14.department.ATMDepartment;

import java.math.BigDecimal;

public class AtmDepartmentTest {
  private ATMDepartment atmDepartment;
  private ATM myAtm1;
  private ATM myAtm2;
  private ATM myAtm3;
  private ATM myAtm4;

  @BeforeEach
  public void createATM() {
    atmDepartment = new ATMDepartment();

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
  public void printAllATMBalance() {
    System.out.println("Before operations:");
    atmDepartment.printAllATMBalance();

    someRandomOperations();

    System.out.println("After operations:");
    atmDepartment.printAllATMBalance();
  }

  // TODO: возможно, можнорандомить и в цикеле
  private void someRandomOperations() {
    myAtm1.withdrawMoney(new BigDecimal(12345));
    myAtm2.withdrawMoney(new BigDecimal(1234));
    myAtm3.withdrawMoney(new BigDecimal(123));
    myAtm4.withdrawMoney(new BigDecimal(12));

    myAtm1.withdrawMoney(new BigDecimal(5));
    myAtm2.withdrawMoney(new BigDecimal(5));
    myAtm3.withdrawMoney(new BigDecimal(5));
    myAtm4.withdrawMoney(new BigDecimal(5));
  }
}
