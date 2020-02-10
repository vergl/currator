package io.github.vergl.currator.service;

import io.github.vergl.currator.domain.ExchangeRate;
import io.github.vergl.currator.domain.ecb.EcbEnvelope;
import io.github.vergl.currator.domain.enumeration.Currency;

import java.util.Date;

public interface ExchangeRateService {
    ExchangeRate getExchangeRateByDateAndCurrency(Date date, Currency currency);
    ExchangeRate save(ExchangeRate rate);
    ExchangeRate saveOrUpdate(ExchangeRate rate);
    Long count();
    void writeEcbRatesToDb(EcbEnvelope envelope);
}
