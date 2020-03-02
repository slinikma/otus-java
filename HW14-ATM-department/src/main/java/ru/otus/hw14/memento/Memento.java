package ru.otus.hw14.memento;

public class Memento {
  private final ATMBinFactoryState state;

  Memento(ATMBinFactoryState state) {
    this.state = new ATMBinFactoryState(state);
  }

  ATMBinFactoryState getState() {
    return state;
  }
}
