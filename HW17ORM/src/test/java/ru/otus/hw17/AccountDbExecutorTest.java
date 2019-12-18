package ru.otus.hw17;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("DbExecutor должен корректно выполнять следующие операции над таблицей Account:")
public class AccountDbExecutorTest {

    @BeforeAll
    void setUp() {

    }

    @Test
    @DisplayName("> Добавлять нового пользователя из объекта класса Account ...")
    void shouldCreateUser() {

    }

    @Test
    @DisplayName("> Обновлять информацию о существующем пользователе из объекта класса Account ...")
    void shouldUpdateUser() {

    }

    @Test
    @DisplayName("> Загружать данные пользователя в объект класса Account ...")
    void shouldLoadUser() {

    }

    // TODO: optional
//  @Test
//  @DisplayName("> Елси пользователь с заданным Id существует - обновлять о нём информацию из объекта класса Account, иначе \n " +
//      " добавлять нового пользователя из объекта класса Account ...")
//  void shouldCreateOrUpdateUser() {
//
//  }
}
