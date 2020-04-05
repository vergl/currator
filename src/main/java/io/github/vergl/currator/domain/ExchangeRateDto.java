package io.github.vergl.currator.domain;

import io.github.vergl.currator.domain.enumeration.Currency;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Data
public class ExchangeRateDto {

    private Date date;

    private Currency base;

    private Map<Currency, BigDecimal> rates;
}
