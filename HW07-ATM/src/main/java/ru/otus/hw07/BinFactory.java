package ru.otus.hw07;

public interface BinFactory {
  public Bin getBin(Nominals nominal) throws ATMException;
}
