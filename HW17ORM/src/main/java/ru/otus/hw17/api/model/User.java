package ru.otus.hw17.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.otus.hw17.annotations.MyOrmId;
import ru.otus.hw17.annotations.TraverserSkip;
import ru.otus.hw17.api.model.hibernate.AddressDataSet;
import ru.otus.hw17.api.model.hibernate.PhoneDataSet;

import javax.persistence.*;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
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

  // Указывает на связь между таблицами "один к одному"
  // CascadeType.ALL - рекурсивно обновляем так-же все объекты на которые в классе есть ссылки
  @TraverserSkip
  @Getter
  @OneToOne(targetEntity = AddressDataSet.class, cascade = CascadeType.ALL)
  @JoinColumn(name = "address_id")
  private AddressDataSet addresses;

  // Указывает на связь между таблицами "один ко многим"
  // CascadeType.ALL - рекурсивно обновляем так-же все объекты на которые в классе есть ссылки
  // FetchType.EAGER (по дефолту для OneToMany FetchType.LAZY) - при обращении к сущности User, загружаются связные сущности
  // TODO: как я понял, что в FetchType.LAZY загрузка будет происходить по мере необходимости (но это не точно)
  @TraverserSkip
  @Getter
  @OneToMany(targetEntity = PhoneDataSet.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
  // Specifies a column that is used to maintain the persistent order of a list.
  @OrderColumn
  private List<PhoneDataSet> phone;

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
        Objects.equals(getName(), user.getName()) &&
        Objects.equals(getAddresses(), user.getAddresses()) &&
        Objects.equals(getPhone(), user.getPhone());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName(), getAddresses(), getPhone());
  }
}
