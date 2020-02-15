package ru.otus.hw07;

import java.util.*;

public class USDBinFactory implements BinFactory {

  private Map<Nominals, Bin> bins;

  public USDBinFactory(Long amount) {
    bins = new HashMap<>();

    EnumSet.allOf(NominalsUSD.class).forEach(nominal ->
        bins.put(nominal, new Bin(new Coin(nominal, Currency.getInstance("USD")), amount)));
  }

  @Override
  public Bin getBin(Nominals nominal) throws ATMException {

    Bin bin = bins.get(nominal);

    if (bin.getAmount() > 0) {
      return bin;
    } else {
      throw new ATMException("Bin with " + nominal.toString() + " coins is empty!");
    }
  }
}
