package ru.otus.hw17.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.otus.hw17.annotations.MyOrmId;

import javax.persistence.*;

import java.util.Objects;

@NoArgsConstructor
@Entity
@Table(name = "otus_students")
public class User {

  @Getter
  @MyOrmId
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "user_id")
  private long id;

  @Getter
  @Column(name = "name")
  private String name;


  public User(long id, String name) {
    this.id = id;
    this.name = name;
  }

  public User(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof User)) return false;
    User user = (User) o;
    return getId() == user.getId() &&
        Objects.equals(getName(), user.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName());
  }
}
