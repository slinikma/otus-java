package ru.otus.hw14.atm;

import lombok.Getter;

import java.util.Currency;

public abstract class Coin {
  public abstract String getCoinInfo();
  public abstract Currency getCurrency();
}
