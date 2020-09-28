package io.github.vergl.currator.domain;

import io.github.vergl.currator.domain.enumeration.Currency;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class ExchangeRateDto {

    @ApiModelProperty("Date of exchange rates")
    private Date date;

    @ApiModelProperty("Base currency")
    private Currency base;

    @ApiModelProperty("List of rates for this date to the base currency")
    private Map<Currency, BigDecimal> rates;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Currency getBase() {
        return base;
    }

    public void setBase(Currency base) {
        this.base = base;
    }

    public Map<Currency, BigDecimal> getRates() {
        return rates;
    }

    public void setRates(Map<Currency, BigDecimal> rates) {
        this.rates = rates;
    }
}
