package io.github.vergl.currator.service.tasks;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.vergl.currator.domain.ecb.EcbEnvelope;
import io.github.vergl.currator.service.EcbService;
import io.github.vergl.currator.service.ExchangeRateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InitialExchangeRateDataLoaderTest {
    private InitialExchangeRateDataLoader loader;
    @Mock
    private ExchangeRateService exchangeRateService;
    @Mock
    private EcbService ecbService;

    @BeforeEach
    public void setUp() {
        loader = new InitialExchangeRateDataLoader(exchangeRateService, ecbService);
    }

    @Test
    @DisplayName("loadInitialData() should not call initializeDb() when count() is not equal to zero")
    public void shouldNotLoadHistoryRatesWhenCountIsGreaterThanZero() throws JsonProcessingException {
        when(exchangeRateService.count()).thenReturn(1L);

        loader.loadInitialData();
        verify(exchangeRateService).count();
        verify(ecbService, times(0)).loadHistoryRates();
        verify(exchangeRateService, times(0)).writeEcbRatesToDb(any());
    }

    @Test
    @DisplayName("loadInitialData() should not call writeEcbRatesToDb() " +
            "when JsonProcessingException is thrown")
    public void shouldNotWriteToDbIfJsonProcessingExceptionIsThrown() throws JsonProcessingException {
        when(exchangeRateService.count()).thenReturn(0L);
        when(ecbService.loadHistoryRates()).thenThrow(JsonProcessingException.class);

        loader.loadInitialData();
        verify(exchangeRateService).count();
        verify(ecbService).loadHistoryRates();
        verify(exchangeRateService, times(0)).writeEcbRatesToDb(any());
    }

    @Test
    @DisplayName("loadInitialData() should not call writeEcbRatesToDb() " +
            "when RestClientException is thrown")
    public void shouldNotWriteToDbIfRestClientExceptionIsThrown() throws JsonProcessingException {
        when(exchangeRateService.count()).thenReturn(0L);
        when(ecbService.loadHistoryRates()).thenThrow(RestClientException.class);

        loader.loadInitialData();
        verify(exchangeRateService).count();
        verify(ecbService).loadHistoryRates();
        verify(exchangeRateService, times(0)).writeEcbRatesToDb(any());
    }

    @Test
    @DisplayName("loadInitialData() should call initializeDb()")
    public void shouldWriteToDbWhenCountEqualsToZero() throws JsonProcessingException {
        when(exchangeRateService.count()).thenReturn(0L);
        EcbEnvelope envelope = new EcbEnvelope();
        when(ecbService.loadHistoryRates()).thenReturn(envelope);

        loader.loadInitialData();
        verify(exchangeRateService).count();
        verify(ecbService).loadHistoryRates();
        verify(exchangeRateService).writeEcbRatesToDb(envelope);
    }
}
