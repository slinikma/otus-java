package ru.otus.hw14.atm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import java.lang.reflect.Type;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class USDBinFactory implements BinFactory {

  private Map<NominalsUSD, Bin> bins;

  public USDBinFactory(Long amount) {
    bins = new EnumMap<>(NominalsUSD.class);

    EnumSet.allOf(NominalsUSD.class).forEach(nominal ->
        bins.put(nominal, new Bin(new CoinUSD(nominal), amount)));
  }

  public USDBinFactory(Map<NominalsUSD, Bin> bins) {
    this.bins = bins;
  }

  @Override
  public Map<NominalsUSD, Bin> getAllBins() {
    return bins;
  }

  @Override
  public Map<NominalsUSD, Bin> getAllBinsCopy() {
    //Deep copy
    GsonBuilder gsonBuilder = new GsonBuilder();

    // adding all different container classes with their flag
    final RuntimeTypeAdapterFactory<Coin> coinTypeFactory = RuntimeTypeAdapterFactory
        .of(Coin.class, "currency") // Here you specify which is the parent class and what field particularizes the child class.
        .registerSubtype(CoinUSD.class, "USD");

    Gson gson = gsonBuilder.registerTypeAdapterFactory(coinTypeFactory).create();
    String jsonString = gson.toJson(this.bins);

    Type type = new TypeToken<HashMap<NominalsUSD, Bin>>(){}.getType();

    return gson.fromJson(jsonString, type);
  }

  @Override
  public Bin getBin(Nominals nominal) throws ATMException {

    Bin bin = bins.get(nominal);

    if (bin.getAmount() > 0) {
      return bin;
    } else {
      throw new ATMException("Bin with " + nominal.toString() + " coins is empty!");
    }
  }

  @Override
  public BinFactory clone() {
    return new USDBinFactory(this.getAllBinsCopy());
  }
}
