package io.github.vergl.currator.service.impl;

import io.github.vergl.currator.domain.ExchangeRate;
import io.github.vergl.currator.repository.ExchangeRateRepository;
import io.github.vergl.currator.service.ExchangeRateService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {
    private final ExchangeRateRepository exchangeRateRepository;

    public ExchangeRateServiceImpl(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Override
    public ExchangeRate getExchangeRateByDateAndCurrency(Date date, String currency) {
        return exchangeRateRepository.findByDateAndCurrency(date, currency);
    }

    @Override
    public ExchangeRate save(ExchangeRate rate) {
        return exchangeRateRepository.save(rate);
    }

    @Override
    public Long count() {
        return exchangeRateRepository.count();
    }
}
