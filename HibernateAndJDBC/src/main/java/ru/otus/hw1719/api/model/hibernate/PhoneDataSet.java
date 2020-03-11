package ru.otus.hw1719.api.model.hibernate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.hw1719.api.model.User;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "phone_numbers")
public class PhoneDataSet {

  @Getter
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "phone_id")
  private long id;

  @Getter
  @Column(name = "number")
  private String number;

  @Getter
  @Setter
  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER) // получается двунаправленная связь
  @JoinColumn(name = "user_id")
  private User user;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PhoneDataSet)) return false;
    PhoneDataSet that = (PhoneDataSet) o;
    return getId() == that.getId() &&
        Objects.equals(getNumber(), that.getNumber());
// TODO: не сравниваю User во избежании StackOverflowError
//        Objects.equals(getUser(), that.getUser());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getNumber()); // , getUser()
  }
}
