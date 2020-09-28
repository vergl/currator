package io.github.vergl.currator.domain.mapper;

import io.github.vergl.currator.domain.ExchangeRate;
import io.github.vergl.currator.domain.ExchangeRateDto;
import io.github.vergl.currator.domain.enumeration.Currency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class ExchangeRateMapperTest {

    private ExchangeRateMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = new ExchangeRateMapper();
    }

    @Test
    @DisplayName("entityToDto() with null value should return null")
    public void entityToDto_withNull() {
        ExchangeRateDto result = mapper.entityToDto(null);

        Assertions.assertNull(result);
    }

    @Test
    @DisplayName("entityToDto() with correct value should return new dto")
    public void entityToDto_withEntity() {
        Date newYear2020 = new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime();
        ExchangeRate entity = new ExchangeRate();
        entity.setDate(newYear2020);
        entity.setBaseCurrency(Currency.EUR);
        entity.setCurrency(Currency.USD);
        entity.setRate(1.3);

        ExchangeRateDto result = mapper.entityToDto(entity);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(newYear2020, result.getDate());
        Assertions.assertEquals(Currency.EUR, result.getBase());
        Assertions.assertEquals(1, result.getRates().size());
        Assertions.assertTrue(result.getRates().containsKey(Currency.USD));
        Assertions.assertEquals(BigDecimal.valueOf(1.3), result.getRates().get(Currency.USD));
    }

    @Test
    @DisplayName("entityListToDto() with null value should return null")
    public void entityListToDto_withNull() {
        ExchangeRateDto result = mapper.entityListToDto(null);

        Assertions.assertNull(result);
    }

    @Test
    @DisplayName("entityListToDto() with empty list should return null")
    public void entityListToDto_withEmptyList() {
        ExchangeRateDto result = mapper.entityListToDto(Collections.emptyList());

        Assertions.assertNull(result);
    }


    @Test
    @DisplayName("entityListToDto() with correct values should return new dto")
    public void entityListToDto_withEntities() {
        Date newYear2020 = new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime();
        ExchangeRate entity1 = new ExchangeRate();
        entity1.setDate(newYear2020);
        entity1.setBaseCurrency(Currency.EUR);
        entity1.setCurrency(Currency.USD);
        entity1.setRate(1.3);

        ExchangeRate entity2 = new ExchangeRate();
        entity2.setDate(newYear2020);
        entity2.setBaseCurrency(Currency.EUR);
        entity2.setCurrency(Currency.RUB);
        entity2.setRate(70.0);

        ExchangeRateDto result = mapper.entityListToDto(List.of(entity1, entity2));

        Assertions.assertNotNull(result);
        Assertions.assertEquals(newYear2020, result.getDate());
        Assertions.assertEquals(Currency.EUR, result.getBase());
        Assertions.assertEquals(2, result.getRates().size());
        Assertions.assertTrue(result.getRates().containsKey(Currency.USD));
        Assertions.assertEquals(BigDecimal.valueOf(1.3), result.getRates().get(Currency.USD));
        Assertions.assertTrue(result.getRates().containsKey(Currency.RUB));
        Assertions.assertEquals(BigDecimal.valueOf(70.0), result.getRates().get(Currency.RUB));
    }

    @Test
    @DisplayName("entityListToDto() with values from different days should throw an exception")
    public void entityListToDto_withDifferentDays() {
        Date newYear2020 = new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime();
        Date secondOfJanuary2020 = new GregorianCalendar(2020, Calendar.JANUARY, 2).getTime();
        ExchangeRate entity1 = new ExchangeRate();
        entity1.setDate(newYear2020);
        entity1.setBaseCurrency(Currency.EUR);
        entity1.setCurrency(Currency.USD);
        entity1.setRate(1.3);

        ExchangeRate entity2 = new ExchangeRate();
        entity2.setDate(secondOfJanuary2020);
        entity2.setBaseCurrency(Currency.EUR);
        entity2.setCurrency(Currency.RUB);
        entity2.setRate(70.0);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> mapper.entityListToDto(List.of(entity1, entity2)));
    }

    @Test
    @DisplayName("entityListToDtoList() with null value should return empty list")
    public void entityListToDtoList_withNull() {
        List<ExchangeRateDto> result = mapper.entityListToDtoList(null);

        Assertions.assertEquals(0, result.size());
    }

    @Test
    @DisplayName("entityListToDtoList() with empty list should return empty list")
    public void entityListToDtoList_withEmptyList() {
        List<ExchangeRateDto> result = mapper.entityListToDtoList(Collections.emptyList());

        Assertions.assertEquals(0, result.size());
    }


    @Test
    @DisplayName("entityListToDtoList() with correct values should return new dto list")
    public void entityListToDtoList_withEntities() {
        Date newYear2020 = new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime();
        Date secondOfJanuary2020 = new GregorianCalendar(2020, Calendar.JANUARY, 2).getTime();
        ExchangeRate entity1 = new ExchangeRate();
        entity1.setDate(newYear2020);
        entity1.setBaseCurrency(Currency.EUR);
        entity1.setCurrency(Currency.USD);
        entity1.setRate(1.3);

        ExchangeRate entity2 = new ExchangeRate();
        entity2.setDate(secondOfJanuary2020);
        entity2.setBaseCurrency(Currency.EUR);
        entity2.setCurrency(Currency.USD);
        entity2.setRate(1.4);

        List<ExchangeRateDto> resultList = mapper.entityListToDtoList(List.of(entity1, entity2));

        Assertions.assertNotNull(resultList);
        Assertions.assertEquals(2, resultList.size());

        ExchangeRateDto dto1 = resultList.get(0);
        Assertions.assertEquals(newYear2020, dto1.getDate());
        Assertions.assertEquals(Currency.EUR, dto1.getBase());
        Assertions.assertEquals(1, dto1.getRates().size());
        Assertions.assertTrue(dto1.getRates().containsKey(Currency.USD));
        Assertions.assertEquals(BigDecimal.valueOf(1.3), dto1.getRates().get(Currency.USD));

        ExchangeRateDto dto2 = resultList.get(1);
        Assertions.assertEquals(secondOfJanuary2020, dto2.getDate());
        Assertions.assertEquals(Currency.EUR, dto2.getBase());
        Assertions.assertEquals(1, dto2.getRates().size());
        Assertions.assertTrue(dto2.getRates().containsKey(Currency.USD));
        Assertions.assertEquals(BigDecimal.valueOf(1.4), dto2.getRates().get(Currency.USD));
    }
}
