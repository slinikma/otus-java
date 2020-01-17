package ru.otus.hw24.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.NonFinal;

// Заменил ru.otus.user.User -> ru.otus.hw24.domain.User
// private final -> @NonFinal
// TODO: почему так? почему не сразу создавать объект с полями, а задавать их потом через сеттеры?
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
  @NonFinal
  String login;

  @NonFinal
  String password;
}
