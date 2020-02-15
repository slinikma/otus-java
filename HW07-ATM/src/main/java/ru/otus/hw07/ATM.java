package ru.otus.hw07;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.EnumSet;

public class ATM {

  private BinFactory binFactory;

  public static BinFactory makeFactory(Currency currency) {
    switch (currency.getCurrencyCode()) {
      case "USD":
        return new USDBinFactory(1000L);
      default:
        throw new IllegalArgumentException("KingdomType not supported.");
    }
  }

  public ATM() {
     this.binFactory = ATM.makeFactory(Currency.getInstance("USD"));
  }

  public void withdrawMoney(BigDecimal requestedCash) {

    System.out.println("\n\nWithdraw " + requestedCash + " USD");

    for (Nominals nominal : EnumSet.allOf(NominalsUSD.class)) {
      long requestedCoins = requestedCash.divide(nominal.getValue(), RoundingMode.DOWN).longValue();

      try {
        Long availableCoins = this.binFactory.getBin(nominal).withdrawAAvailableCoins(requestedCoins);

        // TODO: можно сделать лист всех купюр, которые собираемся выдать
        if (availableCoins > 0) {
          System.out.println("ATM want to give you " + availableCoins + " coins with nominal " + nominal.getValue());
        }

        requestedCash = requestedCash.subtract(nominal.getValue().multiply(new BigDecimal(availableCoins)));

        if (requestedCash.compareTo(new BigDecimal(0)) == 0) {
          System.out.println("Cool! ATM gives you coins!");
          return;
        }
      }  catch (ATMException e) {
        System.out.println(e.getMessage());
      }
    }

    System.out.println("Woops! It seems like ATM doesn't have enough coins for you :C");
  }

  public void replenish(Nominals nominal, Long amount) {

    try {
      this.binFactory.getBin(nominal).putCoins(amount);
      System.out.println("\n\nPutting " + amount + " USD coins with nominal " + nominal.getValue());
    } catch (ATMException e) {
      System.out.println(e.getMessage());
    }
  }

  public void printATMBalance() {
    System.out.println("\n\nCurrent balance: ");
    EnumSet.allOf(NominalsUSD.class).forEach(nominal -> {
      try {
        System.out.println("ATM has " + this.binFactory.getBin(nominal).getAmount() + " coins with nominal " + nominal.getValue());
      } catch (ATMException e) {
        System.out.println(e.getMessage());
      }
    });
  }
}
