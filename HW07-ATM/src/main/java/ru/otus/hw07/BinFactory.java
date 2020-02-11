package ru.otus.hw07;

import java.math.BigDecimal;

public class BinFactory {
  public static Bin getBin(Coin coin, BigDecimal amount) {
    return new Bin(coin, amount);
  }
}
