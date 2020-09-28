package io.github.vergl.currator.domain.mapper;

import io.github.vergl.currator.domain.ExchangeRate;
import io.github.vergl.currator.domain.ExchangeRateDto;
import io.github.vergl.currator.domain.enumeration.Currency;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExchangeRateMapper {

    /**
     * Maps Exchange rate entity from DB to ExchangeRate DTO.
     *
     * @param entity exchange rate from DB
     * @return ExchangeRate DTO
     */
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

    /**
     * Maps list of Exchange rate entities from DB to a single ExchangeRate DTO
     * (used when all items in list from the same date).
     * If you have list of entities with different dates use {@link ExchangeRateMapper#entityListToDtoList} instead.
     *
     * @param entityList list of entities from DB with the same date.
     * @return ExchangeRate DTO
     * @throws IllegalArgumentException if elements in the list of entities don't have the same date.
     */
    public ExchangeRateDto entityListToDto(List<ExchangeRate> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            return null;
        }

        long numberOfUniqueDays = entityList.stream().map(ExchangeRate::getDate).distinct().count();
        if (numberOfUniqueDays != 1) {
            throw new IllegalArgumentException("Elements in entityList should have the same date");
        }

        ExchangeRate firstRate = entityList.get(0);
        ExchangeRateDto dto = new ExchangeRateDto();
        dto.setDate(firstRate.getDate());
        dto.setBase(firstRate.getBaseCurrency());

        Map<Currency, BigDecimal> rates = new EnumMap<>(Currency.class);
        for (ExchangeRate entity : entityList) {
            rates.put(entity.getCurrency(), BigDecimal.valueOf(entity.getRate()));
        }
        dto.setRates(rates);

        return dto;
    }

    /**
     * Maps list of Exchange rate entities from DB to a list of ExchangeRate DTO
     *
     * @param entityList list of entities from DB with the same date.
     * @return List of ExchangeRate DTO
     */
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
