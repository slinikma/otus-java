package ru.otus.hw14.memento;

import java.util.ArrayDeque;
import java.util.Deque;

public class StateOriginator {
  //Фактически, это stack
  private final Deque<Memento> stack = new ArrayDeque<>();

  public void saveState(ATMBinsState state) {
    stack.push(new Memento(state));
  }

  public ATMBinsState restoreState() {
    return stack.pop().getState();
  }
}
