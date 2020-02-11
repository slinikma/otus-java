package ru.otus.hw07;

import lombok.Getter;

import java.math.BigDecimal;

public class Bin {
  private @Getter BigDecimal amount;
  private @Getter Coin coin;

  public Bin(Coin coin, BigDecimal amount) {
    this.coin = coin;
    this.amount = amount;
  }

  public BigDecimal putCoins(Coin coin, BigDecimal amount) {
    if (!this.coin.equals(coin)) {
      throw new IllegalArgumentException("This bin can hold only " + coin.getCoinInfo() + " coins");
    } else {
      this.amount.add(amount);
    }

    return this.amount;
  }

  public BigDecimal getCoins(BigDecimal amount) {
    // this.amount < amount
    if (this.amount.compareTo(amount) == -1) {
      amount = this.amount;
      this.amount = new BigDecimal(0);
      return amount;
    } else {
      this.amount.subtract(amount);
      return amount;
    }
  }
}
