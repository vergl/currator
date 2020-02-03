package io.github.vergl.currator.service;

import io.github.vergl.currator.domain.ExchangeRate;

import java.util.Date;

public interface ExchangeRateService {

    ExchangeRate getExchangeRateByDateAndCurrency(Date date, String currency);

    ExchangeRate save(ExchangeRate rate);

    Long count();
}
