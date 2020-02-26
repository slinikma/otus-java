package ru.otus.hw14.memento;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import ru.otus.hw14.atm.Bin;
import ru.otus.hw14.atm.Nominals;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ATMBinsState {
  @Getter
  private final Map<Nominals, Bin> bins;

  public ATMBinsState(Map<Nominals, Bin> bins) {
    //Deep clone
    Gson gson = new Gson();
    String jsonString = gson.toJson(bins);

    // TODO: Nominals -> USD
    Type type = new TypeToken<HashMap<Nominals, Bin>>(){}.getType();
    this.bins = gson.fromJson(jsonString, type);
  }

  public ATMBinsState(ATMBinsState state) {
    bins = new HashMap<>();
  }
}
