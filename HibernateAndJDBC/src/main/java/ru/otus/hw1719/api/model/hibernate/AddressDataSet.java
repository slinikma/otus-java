package ru.otus.hw1719.api.model.hibernate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "addresses")
public class AddressDataSet {

  @Getter
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "address_id")
  private long id;

  @Getter
  @Column(name = "street")
  private String street;
}
