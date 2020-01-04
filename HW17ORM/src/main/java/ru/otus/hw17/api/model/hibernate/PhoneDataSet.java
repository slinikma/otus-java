package ru.otus.hw17.api.model.hibernate;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
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
}
