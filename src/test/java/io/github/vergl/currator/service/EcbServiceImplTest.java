package io.github.vergl.currator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.vergl.currator.config.properties.EcbProperties;
import io.github.vergl.currator.domain.ecb.EcbEnvelope;
import io.github.vergl.currator.service.impl.EcbServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EcbServiceImplTest {
    private EcbService ecbService;
    @Mock
    private EcbProperties ecbProperties;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private ObjectMapper xmlMapper;

    @BeforeEach
    public void setUp() {
        ecbService = new EcbServiceImpl(ecbProperties, restTemplate, xmlMapper);
    }

    @Test
    @DisplayName("loadHistoryRates() should exchange and deserialize data")
    public void loadHistoryRatesShouldExchangeAndDeserializeData() throws JsonProcessingException {
        String historicalUrl = "historical-url";
        String body = "response-body";
        when(ecbProperties.getHistoricalDataUrl()).thenReturn(historicalUrl);
        when(restTemplate.exchange(historicalUrl, HttpMethod.GET, HttpEntity.EMPTY, String.class))
                .thenReturn(new ResponseEntity<>(body, HttpStatus.OK));

        ecbService.loadHistoryRates();
        verify(restTemplate).exchange(historicalUrl, HttpMethod.GET, HttpEntity.EMPTY, String.class);
        verify(xmlMapper).readValue(body, EcbEnvelope.class);
    }

    @Test
    @DisplayName("loadHistoryRates() should exchange and deserialize data")
    public void loadHistoryRatesShouldThrowRestClientExceptionWhenBodyIsNull() {
        String historicalUrl = "historical-url";
        when(ecbProperties.getHistoricalDataUrl()).thenReturn(historicalUrl);
        when(restTemplate.exchange(historicalUrl, HttpMethod.GET, HttpEntity.EMPTY, String.class))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        assertThrows(RestClientException.class, () -> ecbService.loadHistoryRates());
        verify(restTemplate).exchange(historicalUrl, HttpMethod.GET, HttpEntity.EMPTY, String.class);
    }
}
