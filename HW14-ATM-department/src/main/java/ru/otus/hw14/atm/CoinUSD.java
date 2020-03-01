package ru.otus.hw14.atm;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Currency;

@EqualsAndHashCode
public class CoinUSD extends Coin {
  private @Getter
  NominalsUSD nominal;

  public CoinUSD(NominalsUSD nominal) {
//    super(currency);
    this.nominal = nominal;
  }

  @Override
  public String getCoinInfo() {
    return new StringBuilder().append("Nominal: ")
        .append(nominal)
        .append(" Currency: USD")
        .toString();
  }

  @Override
  public Currency getCurrency() {
    return Currency.getInstance("USD");
  }
}
