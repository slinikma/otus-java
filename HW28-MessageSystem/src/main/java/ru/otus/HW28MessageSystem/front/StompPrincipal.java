package ru.otus.HW28MessageSystem.front;

import lombok.AllArgsConstructor;

import java.security.Principal;

@AllArgsConstructor
public class StompPrincipal implements Principal {

  private String name;

  @Override
  public String getName() {
    return name;
  }
}
