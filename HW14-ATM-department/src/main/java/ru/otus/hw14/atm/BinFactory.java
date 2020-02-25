package ru.otus.hw14.atm;

public interface BinFactory {
  public Bin getBin(Nominals nominal) throws ATMException;
}
