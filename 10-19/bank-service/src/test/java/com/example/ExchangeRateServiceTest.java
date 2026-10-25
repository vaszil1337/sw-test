package com.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ExchangeRateServiceTest {

    @Test
    @DisplayName("when(...).thenReturn(...) működésének tesztelése (8. és 9. lépés)")
    void testExchangeRate() {
        ExchangeRateService rateService =
                mock(ExchangeRateService.class);

        when(
                rateService.getRate("EUR", "HUF")
        ).thenReturn(400.0);

        double rate = rateService.getRate("EUR", "HUF");

        assertEquals(400.0, rate);
        verify(rateService).getRate("EUR", "HUF");
    }
}
