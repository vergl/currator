package io.github.vergl.currator.service;

import io.github.vergl.currator.domain.ExchangeRate;
import io.github.vergl.currator.domain.ExchangeRateDto;
import io.github.vergl.currator.domain.ecb.EcbEnvelope;
import io.github.vergl.currator.domain.filter.ExchangeRateDateFilter;
import io.github.vergl.currator.domain.filter.ExchangeRateHistoryFilter;

import java.util.List;

public interface ExchangeRateService {

    ExchangeRateDto getByDate(ExchangeRateDateFilter filter);

    ExchangeRate saveOrUpdate(ExchangeRate rate);

    Long count();

    void writeEcbRatesToDb(EcbEnvelope envelope);

    List<ExchangeRateDto> getHistoryRates(ExchangeRateHistoryFilter filter);
}
