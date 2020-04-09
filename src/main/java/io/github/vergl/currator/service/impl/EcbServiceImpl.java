package io.github.vergl.currator.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.vergl.currator.config.properties.EcbProperties;
import io.github.vergl.currator.domain.ecb.EcbEnvelope;
import io.github.vergl.currator.service.EcbService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class EcbServiceImpl implements EcbService {

    private final EcbProperties ecbProperties;
    private final RestTemplate restTemplate;
    @Qualifier("xmlMapper")
    private final ObjectMapper xmlMapper;
    
    @Override
    public EcbEnvelope loadHistoryRates() throws JsonProcessingException {
        ResponseEntity<String> response = restTemplate.exchange(
                ecbProperties.getHistoricalDataUrl(),
                HttpMethod.GET,
                HttpEntity.EMPTY,
                String.class
        );
        String body = Optional.ofNullable(response.getBody())
                .orElseThrow(() -> new RestClientException("Empty response body"));
        return xmlMapper.readValue(body, EcbEnvelope.class);
    }
}
