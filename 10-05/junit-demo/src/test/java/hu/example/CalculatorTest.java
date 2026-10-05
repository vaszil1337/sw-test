package hu.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @ParameterizedTest
    @CsvSource({
            "1, 2, 3",
            "3, 4, 7",
            "-1, 1, 0",
            "10, 20, 30"
    })
    void additionWorks(
            int a,
            int b,
            int expected) {

        assertEquals(
                expected,
                calculator.add(a, b)
        );
    }

    @Test
    void subtractionWorks() {
        assertEquals(7, calculator.subtract(10, 3));
    }

    @Test
    void multiplicationWorks() {
        assertEquals(20, calculator.multiply(4, 5));
    }
}