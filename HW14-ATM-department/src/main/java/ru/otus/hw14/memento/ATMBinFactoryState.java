package ru.otus.hw14.memento;

import lombok.Getter;
import ru.otus.hw14.atm.*;

public class ATMBinFactoryState {
  @Getter
  private final BinFactory binFactory;

  public ATMBinFactoryState(BinFactory binFactory) {
    this.binFactory = binFactory;
  }

  public ATMBinFactoryState(ATMBinFactoryState state) {
    this.binFactory = state.getBinFactory().clone();
  }
}
