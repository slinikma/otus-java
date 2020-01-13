package ru.otus.hw17.api.model.myorm;

import com.sun.istack.NotNull;
import lombok.*;
import ru.otus.hw17.annotations.Id;

/*
 * My ORM model
 * */

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Account {
  @Getter @Id private long no;
  @Getter private String type;
  @Getter private int rest;

  // Конструктор без no, т.к. id поле будет генерироваться автомтически
  public Account(String type, int rest) {
    this.type = type;
    this.rest = rest;
  }
}
