package com.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BankServiceTest {

    @Mock
    private NotificationService mockNotificationService;

    @InjectMocks
    private BankService injectedBankService;

    @Test
    @DisplayName("Kézi mock() használata: deposit() értesítést küld és módosítja az egyenleget (4. és 6. lépés)")
    void depositSendsNotification() {
        NotificationService notificationService =
                mock(NotificationService.class);
        BankService bankService =
                new BankService(notificationService);
        BankAccount account =
                new BankAccount();

        bankService.deposit(account, 1000);

        assertEquals(
                1000.0,
                account.getBalance()
        );
        verify(notificationService)
                .send("Deposit successful");
    }

    @Test
    @DisplayName("@Mock és @InjectMocks annotációk használata (10. lépés)")
    void depositSendsNotificationWithAnnotations() {
        BankAccount account = new BankAccount();

        injectedBankService.deposit(account, 500);

        assertEquals(500.0, account.getBalance());
        verify(mockNotificationService)
                .send("Deposit successful");
    }
}
