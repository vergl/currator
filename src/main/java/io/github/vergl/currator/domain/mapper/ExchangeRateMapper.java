package io.github.vergl.currator.domain.mapper;

import io.github.vergl.currator.domain.ExchangeRate;
import io.github.vergl.currator.domain.ExchangeRateDto;
import io.github.vergl.currator.domain.enumeration.Currency;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExchangeRateMapper {

    public ExchangeRateDto entityToDto(ExchangeRate entity) {
        if (entity == null) {
            return null;
        }

        ExchangeRateDto dto = new ExchangeRateDto();
        dto.setDate(entity.getDate());
        dto.setBase(entity.getBaseCurrency());
        dto.setRates(Collections.singletonMap(entity.getCurrency(), BigDecimal.valueOf(entity.getRate())));

        return dto;
    }

    public ExchangeRateDto entityListToDto(List<ExchangeRate> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            return null;
        }

        ExchangeRate firstRate = entityList.get(0);
        ExchangeRateDto dto = new ExchangeRateDto();
        dto.setDate(firstRate.getDate());
        dto.setBase(firstRate.getBaseCurrency());

        Map<Currency, BigDecimal> rates = new HashMap<>();
        for (ExchangeRate entity : entityList) {
            rates.put(entity.getCurrency(), BigDecimal.valueOf(entity.getRate()));
        }
        dto.setRates(rates);

        return dto;
    }

    public List<ExchangeRateDto> entityListToDtoList(List<ExchangeRate> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            return Collections.emptyList();
        }

        return entityList.stream()
                .collect(Collectors.groupingBy(ExchangeRate::getDate, Collectors.toList()))
                .values().stream()
                .map(this::entityListToDto)
                .sorted(Comparator.comparing(ExchangeRateDto::getDate))
                .collect(Collectors.toList());
    }
}
