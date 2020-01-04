package ru.otus.hw17.api.model.hibernate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "otus_students")
public class User implements ru.otus.hw17.api.model.User {

  @Getter
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "user_id")
  private long id;

  @Getter
  @Column(name = "name")
  private String name;

  // Указывает на связь между таблицами "один к одному"
  // CascadeType.ALL - рекурсивно обновляем так-же все объекты на которые в классе есть ссылки
  @Getter
  @OneToOne(targetEntity = AddressDataSet.class, cascade = CascadeType.ALL)
  @JoinColumn(name = "address_id")
  private AddressDataSet addresses;

  // Указывает на связь между таблицами "один ко многим"
  // CascadeType.ALL - рекурсивно обновляем так-же все объекты на которые в классе есть ссылки
  // FetchType.EAGER (по дефолту для OneToMany FetchType.LAZY) - при обращении к сущности User, загружаются связные сущности
  // TODO: как я понял, что в FetchType.LAZY загрузка будет происходить по мере необходимости (но это не точно)
  @Getter
  @OneToMany(targetEntity = PhoneDataSet.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
  private List<PhoneDataSet> phone;
}
