package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BankAccountTest {

    private BankAccount account;

    @BeforeEach
    void setUp() {
        account = new BankAccount();
    }

    @Test
    @DisplayName("1. Új számla egyenlege 0")
    void testInitialBalanceIsZero() {
        assertEquals(0.0, account.getBalance());
    }

    @Test
    @DisplayName("2. deposit(1000) után az egyenleg 1000")
    void testDepositIncreasesBalance() {
        account.deposit(1000);
        assertEquals(1000.0, account.getBalance());
    }

    @Test
    @DisplayName("3. Több befizetés helyesen összeadódik")
    void testMultipleDepositsAddUpCorrectly() {
        account.deposit(500);
        account.deposit(1500);
        assertEquals(2000.0, account.getBalance());
    }

    @Test
    @DisplayName("4. Befizetés után a withdraw() helyesen csökkenti az egyenleget")
    void testWithdrawDecreasesBalance() {
        account.deposit(1000);
        account.withdraw(400);
        assertEquals(600.0, account.getBalance());
    }

    @Test
    @DisplayName("5. A teljes egyenleg felvehető")
    void testFullBalanceWithdrawal() {
        account.deposit(1000);
        account.withdraw(1000);
        assertEquals(0.0, account.getBalance());
    }

    @Test
    @DisplayName("6. Túl nagy összeg felvétele IllegalArgumentException kivételt dob")
    void testWithdrawExceedingBalanceThrowsException() {
        account.deposit(500);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(600));
        assertEquals(500.0, account.getBalance());
    }

    @Test
    @DisplayName("7. Negatív befizetés kivételt dob")
    void testNegativeDepositThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-100));
        assertEquals(0.0, account.getBalance());
    }

    @Test
    @DisplayName("8. 0 összegű befizetés kivételt dob")
    void testZeroDepositThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> account.deposit(0));
        assertEquals(0.0, account.getBalance());
    }

    @Test
    @DisplayName("9. Negatív pénzfelvétel kivételt dob")
    void testNegativeWithdrawThrowsException() {
        account.deposit(500);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(-50));
        assertEquals(500.0, account.getBalance());
    }

    @Test
    @DisplayName("10. 0 összegű pénzfelvétel kivételt dob")
    void testZeroWithdrawThrowsException() {
        account.deposit(500);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(0));
        assertEquals(500.0, account.getBalance());
    }

    @Test
    @DisplayName("11. Sikertelen műveletek után az egyenleg változatlan marad")
    void testBalanceRemainsUnchangedAfterFailedOperations() {
        account.deposit(1000);

        // Sikertelen befizetések
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-200));
        assertThrows(IllegalArgumentException.class, () -> account.deposit(0));
        assertEquals(1000.0, account.getBalance());

        // Sikertelen felvételek
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(1500));
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(-100));
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(0));
        assertEquals(1000.0, account.getBalance());
    }
}
