package ru.otus.hw14.atm;

import lombok.Getter;
import ru.otus.hw14.Listener;
import ru.otus.hw14.memento.ATMBinsState;
import ru.otus.hw14.memento.StateOriginator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.EnumSet;

public class ATM {

  @Getter
  private final Listener listener = cmd -> cmd.execute(this);

  private BinFactory binFactory;
  private StateOriginator stateOriginator;
  @Getter private String atmAddress;

  public static BinFactory makeFactory(Currency currency) {
    switch (currency.getCurrencyCode()) {
      case "USD":
        return new USDBinFactory(1000L);
      default:
        throw new IllegalArgumentException("KingdomType not supported.");
    }
  }

  public ATM(String address) {
    this.binFactory = ATM.makeFactory(Currency.getInstance("USD"));
    this.atmAddress = address;
    this.stateOriginator = new StateOriginator();
    this.stateOriginator.saveState(new ATMBinsState(this.binFactory.getAllBins()));
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
          this.stateOriginator.saveState(new ATMBinsState(this.binFactory.getAllBins()));
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
      this.stateOriginator.saveState(new ATMBinsState(this.binFactory.getAllBins()));
    } catch (ATMException e) {
      System.out.println(e.getMessage());
    }
  }

  public void printATMBalance()
  {
    System.out.println("\n\n" + this.getAtmAddress() + ":\nCurrent balance: " + this.getBalanceInfo());
  }

  public String getBalanceInfo() {

    StringBuilder balanceInfo = new StringBuilder();

    EnumSet.allOf(NominalsUSD.class).forEach(nominal -> {
      try {
        balanceInfo.append("\nATM has " + this.binFactory.getBin(nominal).getAmount() + " coins with nominal " + nominal.getValue());
      } catch (ATMException e) {
        System.out.println(e.getMessage());
      }
    });

    return balanceInfo.toString();
  }
}
