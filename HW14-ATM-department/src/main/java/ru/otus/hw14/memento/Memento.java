package ru.otus.hw14.memento;

public class Memento {
  private final ATMBinsState state;

  Memento(ATMBinsState state) {
    this.state = new ATMBinsState(state);
  }

  ATMBinsState getState() {
    return state;
  }
}
