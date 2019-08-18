package ru.otus.hw07;

import lombok.Getter;

import java.math.BigDecimal;

public class Bin {
  private @Getter BigDecimal amount;
  private @Getter Coin coin;

  // TODO: why is it better to use BigDecimal for money amount?
  public Bin(Coin coin, BigDecimal amount) {
    this.coin = coin;
    this.amount = amount;
  }

  public BigDecimal addMoney(Coin coin, BigDecimal amount) {
    if (!this.coin.equals(coin)) {
      throw new IllegalArgumentException("This bin can hold only " + coin.getCoinInfo() + " coins");
    } else {
      this.amount.add(amount);
    }

    return this.amount;
  }

  public BigDecimal withdrawMoney(BigDecimal amount) {
    return this.amount;
  }
}
