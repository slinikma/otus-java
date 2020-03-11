package ru.otus.hw1719.api.model.myorm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.otus.hw1719.annotations.Id;

/*
 * My ORM model
 * */

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Account {
  @Getter
  @Id private long no;
  @Getter private String type;
  @Getter private int rest;

  // Конструктор без no, т.к. id поле будет генерироваться автомтически
  public Account(String type, int rest) {
    this.type = type;
    this.rest = rest;
  }
}
