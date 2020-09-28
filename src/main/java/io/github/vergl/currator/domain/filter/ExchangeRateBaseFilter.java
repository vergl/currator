package io.github.vergl.currator.domain.filter;

import io.github.vergl.currator.domain.enumeration.Currency;
import io.swagger.annotations.ApiParam;

import java.util.HashSet;
import java.util.Set;

public class ExchangeRateBaseFilter {

    @ApiParam("Base currency")
    private Currency base = Currency.EUR;

    @ApiParam("List of currencies to get information about")
    private Set<Currency> currencies = new HashSet<>(Set.of(Currency.values()));

    public Set<Currency> getCurrencies() {
        if (base != Currency.EUR) {
            currencies.add(base);
        }
        return currencies;
    }

    public Currency getBase() {
        return base;
    }

    public void setBase(Currency base) {
        this.base = base;
    }
}
