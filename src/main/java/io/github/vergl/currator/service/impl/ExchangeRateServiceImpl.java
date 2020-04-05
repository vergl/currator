package io.github.vergl.currator.service.impl;

import io.github.vergl.currator.domain.ExchangeRate;
import io.github.vergl.currator.domain.ExchangeRateDto;
import io.github.vergl.currator.domain.converter.ExchangeRateConverter;
import io.github.vergl.currator.domain.ecb.EcbEnvelope;
import io.github.vergl.currator.domain.enumeration.Currency;
import io.github.vergl.currator.domain.filter.ExchangeRateDateFilter;
import io.github.vergl.currator.domain.filter.ExchangeRateHistoryFilter;
import io.github.vergl.currator.domain.mapper.ExchangeRateMapper;
import io.github.vergl.currator.repository.ExchangeRateRepository;
import io.github.vergl.currator.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final ExchangeRateRepository repository;
    private final ExchangeRateMapper mapper;
    private final ExchangeRateConverter converter;

    @Override
    public ExchangeRateDto getByDate(ExchangeRateDateFilter filter) {
        ExchangeRate rateWithNearestDate = repository.findFirstByDateLessThanEqualOrderByDateDesc(filter.getDate())
                .orElseThrow(IllegalArgumentException::new);
        List<ExchangeRate> rates =
                repository.findByDateAndCurrencyIn(rateWithNearestDate.getDate(), filter.getCurrencies());
        ExchangeRateDto dto = mapper.entityListToDto(rates);
        return converter.convertToBaseCurrency(dto, filter);
    }

    @Override
    public ExchangeRate saveOrUpdate(ExchangeRate newRate) {
        return repository.findFirstByCurrencyAndDateLessThanEqualOrderByDateDesc(newRate.getCurrency(), newRate.getDate())
                .map(foundRate -> {
                    foundRate.setRate(newRate.getRate());
                    return repository.save(foundRate);
                })
                .orElseGet(() -> repository.save(newRate));
    }

    @Override
    public Long count() {
        return repository.count();
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
                                        .forEach(repository::save))
                );
    }

    @Override
    public List<ExchangeRateDto> getHistoryRates(ExchangeRateHistoryFilter filter) {
        List<ExchangeRate> rates = repository.findByDateGreaterThanEqualAndDateLessThanEqualAndCurrencyIn(
                filter.getStartDate(), filter.getEndDate(), filter.getCurrencies());
        List<ExchangeRateDto> dtoList = mapper.entityListToDtoList(rates);
        dtoList.forEach(dto -> converter.convertToBaseCurrency(dto, filter));
        return dtoList;
    }
}
