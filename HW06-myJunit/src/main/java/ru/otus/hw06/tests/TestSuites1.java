package ru.otus.hw06.tests;
import ru.otus.hw06.annotations.AfterEach;
import ru.otus.hw06.annotations.BeforeEach;
import ru.otus.hw06.annotations.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSuites1 {
    private String message1;
    private String message2;

    private Integer num1;
    private Integer num2;

    @BeforeEach
    private void setVars() {
        System.out.println("[BEFORE_1] Set vars");
        message1 = "same";
        message2 = "same2";

        num1 = 101;
        num2 = 1012;
    }

    @Test
    public void testStrings() {
        System.out.println("[TEST_1] 'same' == 'same'");
        System.out.println("Message1: " + message1);
        System.out.println("Message2: " + message2);
        assertEquals(message1, message2);
    }

    @Test
    public void testNumbers() {
        System.out.println("[TEST_1] 101' == 1012");
        assertEquals(num1, num2);
    }

    @AfterEach
    private void printInfo() {
        System.out.println("[AFTER_1] In the @After annotated method");
    }
}
