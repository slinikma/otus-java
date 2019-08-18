package ru.otus.hw07;

import lombok.Getter;

import java.util.Currency;

public class Coin {
  private @Getter
  Nominals nominal;
  private @Getter
  Currency currency;

  public Coin(Nominals nominal, Currency currency) {
    this.nominal = nominal;
    this.currency = currency;
  }

  public String getCoinInfo() {
    return new StringBuilder().append("Nominal: ")
        .append(nominal)
        .append(" Currency: ")
        .append(currency)
        .toString();
  }
}
