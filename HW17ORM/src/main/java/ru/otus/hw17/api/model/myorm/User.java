package ru.otus.hw17.api.model.myorm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.otus.hw17.annotations.Id;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/*
 * My ORM model
 * */

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User implements ru.otus.hw17.api.model.User {
  @Getter
  @Id
  private long id;


  @Getter
  private String name;
}
