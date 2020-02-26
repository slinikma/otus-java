package ru.otus.hw14.department;

import ru.otus.hw14.EventProducer;
import ru.otus.hw14.atm.ATM;
import ru.otus.hw14.commands.PrintBalanceCommand;

import java.util.ArrayList;
import java.util.List;

public class ATMDepartment {

  private List<ATM> atmList;
  EventProducer eventProducer;

  public ATMDepartment() {
    atmList = new ArrayList<>();
    eventProducer  = new EventProducer();
  }

  public void addATM(ATM atm) {
    atmList.add(atm);
    eventProducer.addListener(atm.getListener());
  }

  public void removeATM(ATM atm) {
    atmList.remove(atm);
    eventProducer.removeListener(atm.getListener());
  }

  public void printAllATMBalance() {
    var command = new PrintBalanceCommand();
    eventProducer.event(command);
  }
}
