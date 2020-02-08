package ru.otus.hw06.tests;

import ru.otus.hw06.annotations.AfterEach;
import ru.otus.hw06.annotations.BeforeEach;
import ru.otus.hw06.annotations.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSuites2 {
    private String message1;
    private String message2;

    private Integer num1;
    private Integer num2;

    @BeforeEach
    private void setVars() {
        System.out.println("[BEFORE_2] Set vars");
        message1 = "one";
        message2 = "one1";

        num1 = 10;
        num2 = 100;
    }

    @Test
    public void testStrings() {
        System.out.println("[TEST_2] 'one' == 'one1'");
        assertEquals(message1, message2);
    }

    @Test
    public void testNumbers() {
        System.out.println("[TEST_2] 10 == 100");
        assertEquals(10, 100);
    }

    @AfterEach
    private void printInfo() {
        System.out.println("[AFTER_2] In the @After annotated method");
    }
}
