package ru.otus.hw24.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.NonFinal;

// Заменил ru.otus.user.User -> ru.otus.hw24.domain.User
// private final -> @NonFinal
// TODO: почему так? почему не сразу создавать объект с полями, а задавать их потом через сеттеры?
@AllArgsConstructor
@NoArgsConstructor
public class User {
  @Getter
  @NonFinal
  String login;

  @Getter
  @NonFinal
  String password;
}
