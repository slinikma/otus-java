package ru.otus.hw17.api.model;

import lombok.*;
import ru.otus.hw17.annotations.MyOrmId;


@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Account {
  @Getter
  @MyOrmId
  private long no;
  @Getter
  private String type;
  @Getter
  private int rest;

  public Account(String type, int rest) {
    this.type = type;
    this.rest = rest;
  }
}
