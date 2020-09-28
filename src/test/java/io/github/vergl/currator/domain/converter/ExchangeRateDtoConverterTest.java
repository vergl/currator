package io.github.vergl.currator.domain.converter;

import io.github.vergl.currator.domain.ExchangeRateDto;
import io.github.vergl.currator.domain.enumeration.Currency;
import io.github.vergl.currator.domain.filter.ExchangeRateBaseFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class ExchangeRateDtoConverterTest {

    private ExchangeRateDtoConverter converter;

    @BeforeEach
    public void setUp() {
        converter = new ExchangeRateDtoConverter();
    }

    @Test
    @DisplayName("convertToBaseCurrency() with EUR should return same DTO")
    public void convertToBaseCurrency_currencyEur() {
        Date newYear2020 = new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime();
        ExchangeRateBaseFilter filter = new ExchangeRateBaseFilter();
        filter.setBase(Currency.EUR);
        ExchangeRateDto dto = new ExchangeRateDto();
        dto.setBase(Currency.EUR);
        dto.setDate(newYear2020);
        dto.setRates(Collections.singletonMap(Currency.RUB, BigDecimal.valueOf(70)));

        ExchangeRateDto result = converter.convertToBaseCurrency(dto, filter);

        Assertions.assertEquals(Currency.EUR, result.getBase());
        Assertions.assertEquals(1, result.getRates().size());
        Assertions.assertTrue(result.getRates().containsKey(Currency.RUB));
        Assertions.assertEquals(BigDecimal.valueOf(70), result.getRates().get(Currency.RUB));
    }

    @Test
    @DisplayName("convertToBaseCurrency() with RUB should convert rates")
    public void convertToBaseCurrency_currencyRub() {
        Date newYear2020 = new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime();
        ExchangeRateBaseFilter filter = new ExchangeRateBaseFilter();
        filter.setBase(Currency.RUB);
        ExchangeRateDto dto = new ExchangeRateDto();
        dto.setBase(Currency.EUR);
        dto.setDate(newYear2020);
        dto.setRates(new HashMap<>(Collections.singletonMap(Currency.RUB, BigDecimal.valueOf(70))));

        ExchangeRateDto result = converter.convertToBaseCurrency(dto, filter);

        Assertions.assertEquals(Currency.RUB, result.getBase());
        Assertions.assertEquals(1, result.getRates().size());
        Assertions.assertTrue(result.getRates().containsKey(Currency.EUR));
        Assertions.assertEquals(BigDecimal.ONE.divide(BigDecimal.valueOf(70), 4, RoundingMode.HALF_UP),
                result.getRates().get(Currency.EUR));
    }

    @Test
    @DisplayName("convertToBaseCurrency() with null should throw exception")
    public void convertToBaseCurrency_currencyNotStated() {
        Date newYear2020 = new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime();
        ExchangeRateBaseFilter filter = new ExchangeRateBaseFilter();
        filter.setBase(null);
        ExchangeRateDto dto = new ExchangeRateDto();
        dto.setBase(Currency.EUR);
        dto.setDate(newYear2020);
        dto.setRates(new HashMap<>(Collections.singletonMap(Currency.RUB, BigDecimal.valueOf(70))));

        Assertions.assertThrows(IllegalArgumentException.class, () -> converter.convertToBaseCurrency(dto, filter));
    }


}
