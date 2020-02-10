package io.github.vergl.currator.service;

import io.github.vergl.currator.domain.ExchangeRate;
import io.github.vergl.currator.domain.ecb.EcbEnvelope;
import io.github.vergl.currator.domain.ecb.EcbRate;
import io.github.vergl.currator.domain.ecb.EcbRatesByDate;
import io.github.vergl.currator.domain.enumeration.Currency;
import io.github.vergl.currator.repository.ExchangeRateRepository;
import io.github.vergl.currator.service.impl.ExchangeRateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExchangeRateServiceImplTest {
    private ExchangeRateService exchangeRateService;
    @Mock
    private ExchangeRateRepository exchangeRateRepository;

    @BeforeEach
    public void setUp() {
        exchangeRateService = new ExchangeRateServiceImpl(exchangeRateRepository);
    }

    @Test
    @DisplayName("getExchangeRateByDateAndCurrency() should return ExchangeRate")
    public void getExchangeRateByDateAndCurrencyShouldReturnExchangeRate() {
        Date newYear2020 = new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime();
        ExchangeRate exchangeRate = ExchangeRate.builder()
                .date(newYear2020)
                .baseCurrency(Currency.EUR)
                .currency(Currency.USD)
                .rate(1.3)
                .build();
        when(exchangeRateRepository.findByDateAndCurrency(newYear2020, Currency.USD))
                .thenReturn(exchangeRate);

        var result = exchangeRateService.getExchangeRateByDateAndCurrency(newYear2020, Currency.USD);
        assertNotNull(result);
        assertAll(
                "getExchangeRateByDateAndCurrency() should return valid object",
                () -> assertEquals(newYear2020, result.getDate()),
                () -> assertEquals(Currency.USD, result.getCurrency()),
                () -> assertEquals(Currency.EUR, result.getBaseCurrency()),
                () -> assertEquals(1.3, result.getRate())
        );
        verify(exchangeRateRepository).findByDateAndCurrency(newYear2020, Currency.USD);
    }

    @Test
    @DisplayName("save() should return same ExchangeRate object")
    public void saveShouldReturnSameExchangeRateObject() {
        Date newYear2020 = new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime();
        ExchangeRate exchangeRate = ExchangeRate.builder()
                .date(newYear2020)
                .baseCurrency(Currency.EUR)
                .currency(Currency.USD)
                .rate(1.3)
                .build();
        when(exchangeRateRepository.save(exchangeRate)).thenReturn(exchangeRate);

        var result = exchangeRateService.save(exchangeRate);
        assertNotNull(result);
        assertAll(
                "getExchangeRateByDateAndCurrency() should return valid object",
                () -> assertEquals(newYear2020, result.getDate()),
                () -> assertEquals(Currency.USD, result.getCurrency()),
                () -> assertEquals(Currency.EUR, result.getBaseCurrency()),
                () -> assertEquals(1.3, result.getRate())
        );
        verify(exchangeRateRepository).save(exchangeRate);
    }

    @Test
    @DisplayName("saveOrUpdate() should save new object if old is not found")
    public void saveOrUpdateShouldSaveNewObjectIfOldIsNotFound() {
        Date newYear2020 = new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime();
        ExchangeRate exchangeRate = ExchangeRate.builder()
                .date(newYear2020)
                .baseCurrency(Currency.EUR)
                .currency(Currency.USD)
                .rate(1.3)
                .build();
        when(exchangeRateService.getExchangeRateByDateAndCurrency(newYear2020, Currency.USD))
                .thenReturn(null);
        when(exchangeRateService.save(exchangeRate))
                .thenReturn(exchangeRate);

        ExchangeRate result = exchangeRateService.saveOrUpdate(exchangeRate);
        assertNotNull(result);
        assertAll(
                "getExchangeRateByDateAndCurrency() should return valid object",
                () -> assertEquals(newYear2020, result.getDate()),
                () -> assertEquals(Currency.USD, result.getCurrency()),
                () -> assertEquals(Currency.EUR, result.getBaseCurrency()),
                () -> assertEquals(1.3, result.getRate())
        );
        Mockito.verify(exchangeRateRepository).save(exchangeRate);
    }

    @Test
    @DisplayName("saveOrUpdate() should save new object if old is not found")
    public void saveOrUpdateShouldUpdateOldObjectIfFound() {
        Date newYear2020 = new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime();
        ExchangeRate exchangeRate = ExchangeRate.builder()
                .date(newYear2020)
                .baseCurrency(Currency.EUR)
                .currency(Currency.USD)
                .rate(1.3)
                .build();
        ExchangeRate rateInDb = ExchangeRate.builder()
                .id(1L)
                .date(newYear2020)
                .baseCurrency(Currency.EUR)
                .currency(Currency.USD)
                .rate(1.22)
                .build();
        ExchangeRate updatedRateInDb = ExchangeRate.builder()
                .id(1L)
                .date(newYear2020)
                .baseCurrency(Currency.EUR)
                .currency(Currency.USD)
                .rate(1.3)
                .build();
        when(exchangeRateService.getExchangeRateByDateAndCurrency(newYear2020, Currency.USD))
                .thenReturn(rateInDb);
        when(exchangeRateService.save(updatedRateInDb))
                .thenReturn(updatedRateInDb);

        ExchangeRate result = exchangeRateService.saveOrUpdate(exchangeRate);
        assertNotNull(result);
        assertAll(
                "getExchangeRateByDateAndCurrency() should return valid object",
                () -> assertEquals(1, result.getId()),
                () -> assertEquals(newYear2020, result.getDate()),
                () -> assertEquals(Currency.USD, result.getCurrency()),
                () -> assertEquals(Currency.EUR, result.getBaseCurrency()),
                () -> assertEquals(1.3, result.getRate())
        );
        Mockito.verify(exchangeRateRepository).save(updatedRateInDb);
    }

    @Test
    @DisplayName("count() should return Long object")
    public void countShouldReturnLong() {
        when(exchangeRateRepository.count()).thenReturn(1L);

        var result = exchangeRateService.count();
        assertNotNull(result);
        assertEquals(1L, result);
        verify(exchangeRateRepository).count();
    }

    @Test
    @DisplayName("writeEcbRatesToDb() should iterate over rates")
    public void writeEcbRatesToDbShouldIterateOverRates() {
        Date newYear2020 = new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime();
        EcbRatesByDate ratesByDate = new EcbRatesByDate();
        ratesByDate.setDate(newYear2020);
        ratesByDate.setRates(Arrays.asList(
                new EcbRate(Currency.USD, 1.3),
                new EcbRate(Currency.RUB, 999.9)
        ));
        EcbEnvelope ecbEnvelope = new EcbEnvelope();
        ecbEnvelope.setRates(Collections.singletonList(ratesByDate));

        exchangeRateService.writeEcbRatesToDb(ecbEnvelope);
        verify(exchangeRateRepository, times(2)).save(any(ExchangeRate.class));
        verify(exchangeRateRepository).save(ExchangeRate.builder()
                .date(ratesByDate.getDate())
                .baseCurrency(Currency.EUR)
                .currency(Currency.USD)
                .rate(1.3)
                .build());
        verify(exchangeRateRepository).save(ExchangeRate.builder()
                .date(ratesByDate.getDate())
                .baseCurrency(Currency.EUR)
                .currency(Currency.RUB)
                .rate(999.9)
                .build());
    }

}
