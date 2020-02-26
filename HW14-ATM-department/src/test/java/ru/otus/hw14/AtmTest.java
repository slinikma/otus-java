package ru.otus.hw14;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw14.atm.ATM;
import ru.otus.hw14.department.ATMDepartment;

import java.math.BigDecimal;

public class AtmTest {
  private ATMDepartment atmDepartment;
  private ATM myAtm;

  @BeforeEach
  public void createATM() {
    atmDepartment = new ATMDepartment();
    atmDepartment.addATM(new ATM("Pushkina st."));
    atmDepartment.addATM(new ATM("Kolotushkina st."));
    atmDepartment.addATM(new ATM("Biberevo st."));
    atmDepartment.addATM(new ATM("Balashiha st."));
  }

  @Test
  public void withdrawMoney() {
    myAtm.withdrawMoney(BigDecimal.valueOf(1246));
  }

  @Test
  public void printBalance() {
    atmDepartment.printAllATMBalance();
//    myAtm.printATMBalance();
//    myAtm.withdrawMoney(BigDecimal.valueOf(124542));
//    myAtm.withdrawMoney(BigDecimal.valueOf(2.23));
//    myAtm.printATMBalance();
  }

  @Test
  public void putMoney() {
//    myAtm.printATMBalance();
//    myAtm.replenish(NominalsUSD.DOLLAR, 10L);
//    myAtm.replenish(NominalsUSD.PENNY, 15L);
//    myAtm.replenish(NominalsUSD.HUNDRED_DOLLARS, 11L);
//    myAtm.printATMBalance();
  }

  @Test
  public void notEnoughMoneyError() {
    myAtm.withdrawMoney(BigDecimal.valueOf(99999999999L));
  }
}
