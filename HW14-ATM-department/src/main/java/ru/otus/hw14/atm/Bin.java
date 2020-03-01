package ru.otus.hw14.atm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Bin {
  private @Getter Coin coin;
  private @Getter Long amount;

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
