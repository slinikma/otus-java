package ru.otus.hw14.department;

import ru.otus.hw14.DepartmentObserver;
import ru.otus.hw14.atm.ATM;
import ru.otus.hw14.commands.Command;
import ru.otus.hw14.commands.PrintBalanceCommand;

import java.util.ArrayList;
import java.util.List;

public class ATMDepartment {

  private List<DepartmentObserver> observers;

  public void addObserver(DepartmentObserver observer) {
    observers.add(observer);
  }

  public void removeObserver(DepartmentObserver observer) {
    observers.remove(observer);
  }

  public ATMDepartment() {
    observers = new ArrayList<>();
  }

  public void printAllATMBalance() {
    var command = new PrintBalanceCommand();
    sendCommandToATMs(command);
  }

  private void sendCommandToATMs(Command command) {

    for (var obs : observers) {
      command.execute((ATM) obs);
    }
  }
}
