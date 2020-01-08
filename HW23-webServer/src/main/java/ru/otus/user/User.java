package ru.otus.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class User {
  @Getter
  private final String login;
  @Getter
  private final String password;
}
