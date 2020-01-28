package ru.otus.HW28MessageSystem.domain;

import lombok.*;
import lombok.experimental.NonFinal;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Document(collection = "authenticationData")
public class User {

  @Id
  @NonFinal
  String login;

  @NonFinal
  String password;
}
