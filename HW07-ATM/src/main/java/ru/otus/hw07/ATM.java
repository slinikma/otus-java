package ru.otus.hw07;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Currency;
import java.util.EnumSet;

public class ATM {
  private ArrayList<Bin> bins;

  public ATM() {
    bins = new ArrayList<>();
    // TODO: NominalsUSD + "USD" as string -> it could be bad
    EnumSet.allOf(NominalsUSD.class).forEach(nominal ->
      bins.add(BinFactory
          .getBin(new Coin(nominal, Currency.getInstance("USD")), BigDecimal.valueOf(100_000))));
  }

  public void withdrawMoney(BigDecimal amount) {
    EnumSet.allOf(NominalsUSD.class).forEach(nominal ->
        System.out.println("Amount: " + nominal.getValue()));
  }

  public void replenish(Coin coin, BigDecimal amount) {

  }

  public void pringBinStat() {
    bins.forEach( bin -> {
      System.out.println("Coin:\n\t" + bin.getCoin().getCoinInfo() + " Amount: " + bin.getAmount());
    });
  }

  public void printBalance() {
    System.out.println("Balance: " +
        bins.stream()
            .reduce(
                BigDecimal.ZERO,
                (partialAgeResult, bin) ->
                    partialAgeResult.add(bin.getAmount().multiply(bin.getCoin().getNominal().getValue())),
                    BigDecimal::add
            )
        .setScale(2, RoundingMode.HALF_DOWN)
    );
  }
}
