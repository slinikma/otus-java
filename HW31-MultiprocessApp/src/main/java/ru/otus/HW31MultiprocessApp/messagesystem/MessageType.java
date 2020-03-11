package ru.otus.HW31MultiprocessApp.messagesystem;

public enum MessageType {
  USER_DATA("UserData"), // Для CreateUser хэндлеров
  USERS_LIST("UsersList"), // Для GetAllUsers хэндлеров
  ERRORS("Errors"); // Для Error хэндлеров

  private final String value;

  MessageType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
