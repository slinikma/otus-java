package ru.otus.HW28MessageSystem.messagesystem;

public enum MessageType {
  USER_DATA("UserData"), // Для CreateUser хэндлеров
  USERS_LIST("UsersList"); // Для GetAllUsers хэндлеров

  private final String value;

  MessageType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
