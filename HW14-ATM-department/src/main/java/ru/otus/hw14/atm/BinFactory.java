package ru.otus.hw14.atm;

import java.util.Map;

public interface BinFactory extends Cloneable {
  public Map<? extends Nominals, Bin> getAllBins();
  public Map<? extends Nominals, Bin> getAllBinsCopy();
  public Bin getBin(Nominals nominal) throws ATMException;
  public BinFactory clone();
}
