package io.github.vergl.currator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.vergl.currator.domain.ecb.EcbEnvelope;

public interface EcbService {
    EcbEnvelope loadHistoryRates() throws JsonProcessingException;
}
