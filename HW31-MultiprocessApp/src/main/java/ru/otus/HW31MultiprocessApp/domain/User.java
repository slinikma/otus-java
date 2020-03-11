package ru.otus.HW31MultiprocessApp.domain;

import lombok.*;
import lombok.experimental.NonFinal;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Document(collection = "authenticationData")
public class User implements Serializable {

//  @Id
  @Indexed(unique = true)
  @NonFinal
  String login;

  @NonFinal
  String password;
}
