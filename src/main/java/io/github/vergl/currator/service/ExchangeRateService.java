package io.github.vergl.currator.service;

import io.github.vergl.currator.domain.ExchangeRate;
import io.github.vergl.currator.domain.ExchangeRateDto;
import io.github.vergl.currator.domain.ecb.EcbEnvelope;
import io.github.vergl.currator.domain.filter.ExchangeRateDateFilter;
import io.github.vergl.currator.domain.filter.ExchangeRateHistoryFilter;

import java.util.List;

public interface ExchangeRateService {

    /**
     * Get exchange rates for a chosen date
     *
     * @param filter includes date, base currency and list of currencies
     * @return exchange Rate
     */
    ExchangeRateDto getByDate(ExchangeRateDateFilter filter);

    /**
     * Save new or update existing Exchange rate
     *
     * @param rate exchange rate to update
     * @return updated Exchange rate
     */
    ExchangeRate saveOrUpdate(ExchangeRate rate);

    /**
     * Get a number of exchange rates in the database
     *
     * @return number of exchange rates
     */
    Long count();

    /**
     * Write parsed data from European Central Bank to the database.
     *
     * @param envelope data from ECB
     */
    void writeEcbRatesToDb(EcbEnvelope envelope);

    /**
     * Get exchange rates by history filter
     *
     * @param filter includes start date, end date, base currency and currencies
     * @return list of exchange rates
     */
    List<ExchangeRateDto> getHistoryRates(ExchangeRateHistoryFilter filter);
}
