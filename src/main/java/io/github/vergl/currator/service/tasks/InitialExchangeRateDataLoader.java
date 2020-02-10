package io.github.vergl.currator.service.tasks;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.vergl.currator.domain.ecb.EcbEnvelope;
import io.github.vergl.currator.service.EcbService;
import io.github.vergl.currator.service.ExchangeRateService;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

@Log4j2
@Service
public class InitialExchangeRateDataLoader {
    private final ExchangeRateService exchangeRateService;
    private final EcbService ecbService;

    public InitialExchangeRateDataLoader(ExchangeRateService exchangeRateService,
                                         EcbService ecbService) {
        this.exchangeRateService = exchangeRateService;
        this.ecbService = ecbService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadInitialData() {
        log.info("Database initialization has started.");
        if (exchangeRateService.count() == 0) {
            log.info("Database is empty. Initializing...");
            try {
                initializeDb();
            } catch (RestClientException e) {
                log.error("An error occurred while requesting ECB service.", e);
            } catch (JsonProcessingException e) {
                log.error("An error occurred while initializing database.", e);
            }
        } else {
            log.info("Database is not empty");
        }
        log.info("Database initialization has finished.");
    }

    private void initializeDb() throws JsonProcessingException {
        long startTime = System.currentTimeMillis();
        log.info("Loading data from European Central Bank...");
        EcbEnvelope envelope = ecbService.loadHistoryRates();
        log.info("Writing rates to the database...");
        exchangeRateService.writeEcbRatesToDb(envelope);
        long endTime = System.currentTimeMillis();
        log.info("Database initialized with historical data. Time elapsed: {} sec.",
                (endTime - startTime) / 1000);
    }
}
