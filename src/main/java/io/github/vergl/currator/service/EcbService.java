package io.github.vergl.currator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.vergl.currator.domain.ecb.EcbEnvelope;

public interface EcbService {

    /**
     * Load all previous exchange currencies data to a database
     *
     * @return parsed data from European Central Bank
     * @throws JsonProcessingException when Jackson can't parse response body from ECB
     */
    EcbEnvelope loadHistoryRates() throws JsonProcessingException;
}
