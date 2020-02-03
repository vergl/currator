package io.github.vergl.currator.service;

import io.github.vergl.currator.domain.ExchangeRate;
import io.github.vergl.currator.repository.ExchangeRateRepository;
import io.github.vergl.currator.service.impl.ExchangeRateServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@ExtendWith(MockitoExtension.class)
public class ExchangeRateServiceImplTest {
    ExchangeRateService exchangeRateService;

    @Mock
    private ExchangeRateRepository exchangeRateRepository;

    @BeforeEach
    public void setUp() {
        Date newYear2020 = new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime();
        String currency = "USD";
        ExchangeRate exchangeRate = ExchangeRate.builder()
                .id(1L)
                .date(newYear2020)
                .baseCurrency("EUR")
                .currency(currency)
                .rate(1.3)
                .build();
        exchangeRateService = new ExchangeRateServiceImpl(exchangeRateRepository);
        Mockito.lenient().when(exchangeRateRepository.findByDateAndCurrency(newYear2020, currency))
                .thenReturn(exchangeRate);
        Mockito.lenient().when(exchangeRateRepository.save(exchangeRate))
                .thenReturn(exchangeRate);
        Mockito.lenient().when(exchangeRateRepository.count())
                .thenReturn(1L);
    }

    @Test
    @DisplayName("getExchangeRateByDateAndCurrency() should return ExchangeRate")
    public void getExchangeRateByDateAndCurrencyShouldReturnExchangeRate() {
        Date newYear2020 = new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime();
        String currency = "USD";
        var result = exchangeRateService.getExchangeRateByDateAndCurrency(newYear2020, currency);
        Assertions.assertNotNull(result);
        Assertions.assertAll(
                "getExchangeRateByDateAndCurrency() should return valid object",
                () -> Assertions.assertEquals(1L, result.getId()),
                () -> Assertions.assertEquals(newYear2020, result.getDate()),
                () -> Assertions.assertEquals("USD", result.getCurrency()),
                () -> Assertions.assertEquals("EUR", result.getBaseCurrency()),
                () -> Assertions.assertEquals(1.3, result.getRate())
        );

        Mockito.verify(exchangeRateRepository).findByDateAndCurrency(newYear2020, currency);
    }

    @Test
    @DisplayName("save() should return same ExchangeRate object")
    public void saveShouldReturnSameExchangeRateObject() {
        Date newYear2020 = new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime();
        String currency = "USD";
        ExchangeRate exchangeRate = ExchangeRate.builder()
                .id(1L)
                .date(newYear2020)
                .baseCurrency("EUR")
                .currency(currency)
                .rate(1.3)
                .build();

        var result = exchangeRateService.save(exchangeRate);
        Assertions.assertNotNull(result);
        Assertions.assertAll(
                "getExchangeRateByDateAndCurrency() should return valid object",
                () -> Assertions.assertEquals(1L, result.getId()),
                () -> Assertions.assertEquals(newYear2020, result.getDate()),
                () -> Assertions.assertEquals(currency, result.getCurrency()),
                () -> Assertions.assertEquals("EUR", result.getBaseCurrency()),
                () -> Assertions.assertEquals(1.3, result.getRate())
        );

        Mockito.verify(exchangeRateRepository).save(exchangeRate);
    }

    @Test
    @DisplayName("count() should return Long object")
    public void countShouldReturnLong() {
        var result = exchangeRateService.count();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result);

        Mockito.verify(exchangeRateRepository).count();
    }

}
