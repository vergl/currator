package io.github.vergl.currator.service.impl;

import io.github.vergl.currator.domain.ExchangeRate;
import io.github.vergl.currator.domain.ecb.EcbEnvelope;
import io.github.vergl.currator.domain.enumeration.Currency;
import io.github.vergl.currator.repository.ExchangeRateRepository;
import io.github.vergl.currator.service.ExchangeRateService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Log4j2
@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {
    private final ExchangeRateRepository exchangeRateRepository;

    public ExchangeRateServiceImpl(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Override
    public ExchangeRate getExchangeRateByDateAndCurrency(Date date, Currency currency) {
        return exchangeRateRepository.findByDateAndCurrency(date, currency);
    }

    @Override
    public ExchangeRate save(ExchangeRate rate) {
        return exchangeRateRepository.save(rate);
    }

    @Override
    public ExchangeRate saveOrUpdate(ExchangeRate rate) {
        ExchangeRate foundRate = exchangeRateRepository.findByDateAndCurrency(rate.getDate(), rate.getCurrency());
        if (foundRate == null) {
            return exchangeRateRepository.save(rate);
        } else {
            foundRate.setRate(rate.getRate());
            return exchangeRateRepository.save(foundRate);
        }
    }

    @Override
    public Long count() {
        return exchangeRateRepository.count();
    }

    @Override
    public void writeEcbRatesToDb(EcbEnvelope envelope) {
        Optional.ofNullable(envelope)
                .ifPresent(ecbEnvelope ->
                        ecbEnvelope.getRates().forEach(ratesByDate ->
                                ratesByDate.getRates().stream()
                                        .map(rate -> ExchangeRate.builder()
                                                .date(ratesByDate.getDate())
                                                .baseCurrency(Currency.EUR)
                                                .currency(rate.getCurrency())
                                                .rate(rate.getRate())
                                                .build())
                                        .forEach(exchangeRateRepository::save))
                );
    }
}
