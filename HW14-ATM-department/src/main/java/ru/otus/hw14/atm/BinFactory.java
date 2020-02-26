package ru.otus.hw14.atm;

import java.util.Map;

public interface BinFactory {
  public Map<Nominals, Bin> getAllBins();
  public Bin getBin(Nominals nominal) throws ATMException;
}
