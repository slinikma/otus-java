package ru.otus.hw07;

import lombok.Getter;

public class Bin {
  private @Getter Long amount;
  private @Getter Coin coin; // TODO: можно и не хранить Coin, т.к. нужная фабрика ссылается на объект Bin-а

  public Bin(Coin coin, Long amount) {
    this.coin = coin;
    this.amount = amount;
  }

  public void putCoins(Long amount) {
    this.amount += amount;
  }

  public Long withdrawAAvailableCoins(Long requestedCoins) {
    if (amount < requestedCoins) {
      Long amount = this.amount;
      this.amount = 0L;
      return amount;
    } else {
      this.amount -= requestedCoins;
      return requestedCoins;
    }
  }
}
