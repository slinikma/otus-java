package ru.otus.hw14.atm;

import lombok.Getter;
import ru.otus.hw14.Listener;
import ru.otus.hw14.memento.ATMBinFactoryState;
import ru.otus.hw14.memento.StateOriginator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class ATM {

  @Getter
  private final Listener listener = cmd -> {
    try {
      cmd.execute(this);
    } catch (ATMException e) {
      e.printStackTrace();
    }
  };

  private BinFactory binFactory;
  @Getter private StateOriginator stateOriginator;
  @Getter private String atmAddress;

  public static BinFactory makeFactory(Currency currency) {
    switch (currency.getCurrencyCode()) {
      case "USD":
        return new USDBinFactory(1000L);
      default:
        throw new IllegalArgumentException("Currency is not supported.");
    }
  }

  public ATM(String address) {
    this.binFactory = ATM.makeFactory(Currency.getInstance("USD"));
    this.atmAddress = address;
    this.stateOriginator = new StateOriginator();
    this.stateOriginator.saveState(new ATMBinFactoryState(this.binFactory));
  }

  public Map<Coin, Long> withdrawMoney(BigDecimal requestedCash) throws ATMException {

    Map<Coin, Long> coins = new HashMap<>();

    System.out.println("\n\n[ATM at " + atmAddress + "] Withdraw " + requestedCash.setScale(2, RoundingMode.HALF_EVEN) + " USD");

    for (NominalsUSD nominal : EnumSet.allOf(NominalsUSD.class)) {
      long requestedCoins = requestedCash.divide(nominal.getValue(), RoundingMode.HALF_EVEN).setScale(2, RoundingMode.HALF_EVEN).longValue();

      try {
        Long availableCoins = this.binFactory.getBin(nominal).withdrawAAvailableCoins(requestedCoins);

        if (availableCoins > 0) {
          coins.put(new CoinUSD(nominal), availableCoins);
          System.out.println("[ATM at " + atmAddress + "] want to give you " + availableCoins + " coins with nominal " + nominal.getValue());
        }

        requestedCash = requestedCash.setScale(2, RoundingMode.HALF_EVEN).subtract(nominal.getValue().multiply(new BigDecimal(availableCoins)));

        if (requestedCash.compareTo(new BigDecimal(0)) == 0) {
          System.out.println("Cool! " + "[ATM at " + atmAddress + "] gives you coins!");
          this.stateOriginator.saveState(new ATMBinFactoryState(this.binFactory));
          return coins;
        }
      }  catch (ATMException e) {
        System.out.println(e.getMessage());
      }
    }

    throw new ATMException("Woops! It seems like " + "[ATM at " + atmAddress + "] doesn't have enough coins for you :C");
  }

  public void replenish(Nominals nominal, Long amount) {

    try {
      this.binFactory.getBin(nominal).putCoins(amount);
      System.out.println("\n\n [ATM at " + atmAddress + "] Putting " + amount + " USD coins with nominal " + nominal.getValue());
      this.stateOriginator.saveState(new ATMBinFactoryState(this.binFactory));
    } catch (ATMException e) {
      System.out.println(e.getMessage());
    }
  }

  public void printATMBalance()
  {
    System.out.println("\n\n" + this.getAtmAddress() + ":\nCurrent balance: " + this.getBalanceSrting());
  }

  public String getBalanceSrting() {

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

  public Map<Coin, Long> getBalanceMap() {

    Map<Coin, Long> coins = new HashMap<>();

    EnumSet.allOf(NominalsUSD.class).forEach(nominal -> {
      try {
        coins.put(new CoinUSD(nominal), this.binFactory.getBin(nominal).getAmount());
      } catch (ATMException e) {
        e.printStackTrace();
      }
    });

    return coins;
  }

  public BigDecimal getTotalCash() throws ATMException {

    BigDecimal sum = new BigDecimal(0);

    for (NominalsUSD nominal : EnumSet.allOf(NominalsUSD.class)) {
      sum = sum.add(new BigDecimal(binFactory.getBin(nominal).getAmount()).multiply(nominal.getValue())).setScale(2, RoundingMode.HALF_EVEN);
    }
    return sum;
  }

  public Boolean restorePreviousState() {

    ATMBinFactoryState restoredState = stateOriginator.restoreState();

    if (restoredState != null) {
      binFactory = restoredState.getBinFactory().clone();
      return Boolean.TRUE;
    } else {
      return Boolean.FALSE;
    }
  }
}
