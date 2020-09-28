package io.github.vergl.currator.domain.converter;

import io.github.vergl.currator.domain.ExchangeRateDto;
import io.github.vergl.currator.domain.enumeration.Currency;
import io.github.vergl.currator.domain.filter.ExchangeRateBaseFilter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Service
public class ExchangeRateDtoConverter {

    /**
     * Converts DTO from one base currency to another
     * according to ExchangeRateBaseFilter.
     *
     * @param dto    DTO to convert
     * @param filter request filter
     * @return DTO with rates converted to another base currency
     */
    public ExchangeRateDto convertToBaseCurrency(ExchangeRateDto dto, ExchangeRateBaseFilter filter) {
        Currency newBaseCurrency = filter.getBase();
        Currency currentBaseCurrency = dto.getBase();
        if (newBaseCurrency == currentBaseCurrency) {
            return dto;
        }

        Map<Currency, BigDecimal> currentRates = dto.getRates();
        BigDecimal newBaseRate = currentRates.remove(newBaseCurrency);
        if (newBaseRate == null) {
            throw new IllegalArgumentException();
        }

        currentRates.replaceAll((curr, rate) -> rate = rate.divide(newBaseRate, 4, RoundingMode.HALF_UP));
        if (filter.getCurrencies().contains(currentBaseCurrency)) {
            currentRates.put(currentBaseCurrency, BigDecimal.ONE.divide(newBaseRate, 4, RoundingMode.HALF_UP));
        }
        dto.setBase(newBaseCurrency);
        return dto;
    }
}
