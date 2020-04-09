package io.github.vergl.currator.domain.filter;

import io.github.vergl.currator.domain.enumeration.Currency;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class ExchangeRateBaseFilter {

    private Currency base = Currency.EUR;

    private Set<Currency> currencies = new HashSet<>(Set.of(Currency.values()));

    public Set<Currency> getCurrencies() {
        if (base != Currency.EUR) {
            currencies.add(base);
        }
        return currencies;
    }
}
