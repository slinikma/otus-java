package ru.otus.hw17.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.otus.hw17.annotations.Id;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
  @Getter @Id private long id;
  @Getter private String name;
}
