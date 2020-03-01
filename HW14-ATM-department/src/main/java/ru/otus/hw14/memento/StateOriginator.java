package ru.otus.hw14.memento;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

public class StateOriginator {
  //Фактически, это stack
  private final Deque<Memento> stack = new ArrayDeque<>();

  public void saveState(ATMBinFactoryState state) {
    stack.push(new Memento(state));
  }

  public ATMBinFactoryState restoreState() {
    try {
      return stack.pop().getState();
    } catch (NoSuchElementException e) {
      System.out.println("StateOriginator stack is empty!");
      return null;
    }
  }
}
