package ru.otus.hw19.api.model;

import lombok.*;

import javax.persistence.*;

@Table
@Entity
@AllArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id")
  @Getter private long id;

  @Column(name = "name")
  @Getter @Setter private String name;

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
