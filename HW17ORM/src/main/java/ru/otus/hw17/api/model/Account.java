package ru.otus.hw17.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.otus.hw17.annotations.Id;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Account {
  @Getter @Id private long no;
  @Getter private String type;
  @Getter private int rest;
}
