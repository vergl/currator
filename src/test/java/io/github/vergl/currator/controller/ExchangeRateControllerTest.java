package io.github.vergl.currator.controller;

import io.github.vergl.currator.domain.ExchangeRateDto;
import io.github.vergl.currator.domain.enumeration.Currency;
import io.github.vergl.currator.domain.filter.ExchangeRateDateFilter;
import io.github.vergl.currator.domain.filter.ExchangeRateHistoryFilter;
import io.github.vergl.currator.service.ExchangeRateService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExchangeRateControllerTest {

    private ExchangeRateController exchangeRateController;

    @Mock
    private ExchangeRateService exchangeRateService;

    @BeforeEach
    public void setUp() {
        exchangeRateController = new ExchangeRateController(exchangeRateService);
    }

    @Test
    @DisplayName("getLatest() should call getByDate()")
    public void getLatest() {
        ExchangeRateDto dto = new ExchangeRateDto();
        dto.setBase(Currency.EUR);
        dto.setDate(new Date());
        dto.setRates(Collections.singletonMap(Currency.USD, BigDecimal.valueOf(1.3)));
        ExchangeRateDateFilter filter = new ExchangeRateDateFilter();
        when(exchangeRateService.getByDate(filter)).thenReturn(dto);

        ExchangeRateDto result = exchangeRateController.getLatest(filter);

        Assertions.assertEquals(dto, result);

        verify(exchangeRateService).getByDate(filter);
    }

    @Test
    @DisplayName("getRatesByDate() should call getRatesByDate()")
    public void getRatesByDate() {
        ExchangeRateDto dto = new ExchangeRateDto();
        dto.setBase(Currency.EUR);
        dto.setDate(new Date());
        dto.setRates(Collections.singletonMap(Currency.USD, BigDecimal.valueOf(1.3)));
        ExchangeRateDateFilter filter = new ExchangeRateDateFilter();
        when(exchangeRateService.getByDate(filter)).thenReturn(dto);

        ExchangeRateDto result = exchangeRateController.getRatesByDate(filter);

        Assertions.assertEquals(dto, result);

        verify(exchangeRateService).getByDate(filter);
    }

    @Test
    @DisplayName("getHistoryRates() should call getHistoryRates()")
    public void getHistoryRates() {
        ExchangeRateDto dto = new ExchangeRateDto();
        dto.setBase(Currency.EUR);
        dto.setDate(new Date());
        dto.setRates(Collections.singletonMap(Currency.USD, BigDecimal.valueOf(1.3)));
        ExchangeRateHistoryFilter filter = new ExchangeRateHistoryFilter();
        when(exchangeRateService.getHistoryRates(filter)).thenReturn(List.of(dto));

        List<ExchangeRateDto> result = exchangeRateController.getHistoryRates(filter);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(dto, result.get(0));

        verify(exchangeRateService).getHistoryRates(filter);
    }
}
