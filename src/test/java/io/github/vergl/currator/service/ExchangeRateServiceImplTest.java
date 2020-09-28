package io.github.vergl.currator.service;

import io.github.vergl.currator.domain.ExchangeRate;
import io.github.vergl.currator.domain.ExchangeRateDto;
import io.github.vergl.currator.domain.converter.ExchangeRateDtoConverter;
import io.github.vergl.currator.domain.ecb.EcbEnvelope;
import io.github.vergl.currator.domain.ecb.EcbRate;
import io.github.vergl.currator.domain.ecb.EcbRatesByDate;
import io.github.vergl.currator.domain.enumeration.Currency;
import io.github.vergl.currator.domain.filter.ExchangeRateDateFilter;
import io.github.vergl.currator.domain.filter.ExchangeRateHistoryFilter;
import io.github.vergl.currator.domain.mapper.ExchangeRateMapper;
import io.github.vergl.currator.repository.ExchangeRateRepository;
import io.github.vergl.currator.service.impl.ExchangeRateServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExchangeRateServiceImplTest {

    private ExchangeRateService exchangeRateService;

    @Mock
    private ExchangeRateRepository exchangeRateRepository;

    @Mock
    private ExchangeRateMapper exchangeRateMapper;

    @Mock
    private ExchangeRateDtoConverter exchangeRateDtoConverter;

    @BeforeEach
    public void setUp() {
        exchangeRateService = new ExchangeRateServiceImpl(
                exchangeRateRepository,
                exchangeRateMapper,
                exchangeRateDtoConverter);
    }

    @Test
    @DisplayName("getByDate() with incorrect filter date should throw exception")
    public void getByDate_withIncorrectFilterDate() {
        Date oldDate = new GregorianCalendar(1970, Calendar.JANUARY, 1).getTime();
        when(exchangeRateRepository.findFirstByDateLessThanEqualOrderByDateDesc(oldDate))
                .thenReturn(Optional.empty());
        ExchangeRateDateFilter filter = new ExchangeRateDateFilter();
        filter.setDate(oldDate);

        Assertions.assertThrows(IllegalArgumentException.class, () -> exchangeRateService.getByDate(filter));
    }

    @Test
    @DisplayName("getByDate() with correct filter date should return DTO")
    public void getByDate_withCorrectFilter() {
        Date newYear2020 = new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime();
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setDate(newYear2020);
        exchangeRate.setBaseCurrency(Currency.EUR);
        exchangeRate.setCurrency(Currency.USD);
        exchangeRate.setRate(1.3);

        ExchangeRateDto dto = new ExchangeRateDto();
        dto.setDate(newYear2020);
        dto.setBase(Currency.EUR);
        dto.setRates(Collections.singletonMap(Currency.USD, BigDecimal.valueOf(1.3)));

        ExchangeRateDateFilter filter = new ExchangeRateDateFilter();
        filter.setDate(newYear2020);

        when(exchangeRateRepository.findFirstByDateLessThanEqualOrderByDateDesc(newYear2020))
                .thenReturn(Optional.of(exchangeRate));
        when(exchangeRateRepository.findByDateAndCurrencyIn(newYear2020, new HashSet<>(Set.of(Currency.values()))))
                .thenReturn(Collections.singletonList(exchangeRate));
        when(exchangeRateMapper.entityListToDto(Collections.singletonList(exchangeRate)))
                .thenReturn(dto);
        when(exchangeRateDtoConverter.convertToBaseCurrency(dto, filter))
                .thenReturn(dto);

        ExchangeRateDto result = exchangeRateService.getByDate(filter);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(dto, result);

        Mockito.verify(exchangeRateRepository).findFirstByDateLessThanEqualOrderByDateDesc(newYear2020);
        Mockito.verify(exchangeRateRepository).findByDateAndCurrencyIn(newYear2020, new HashSet<>(Set.of(Currency.values())));
        Mockito.verify(exchangeRateMapper).entityListToDto(Collections.singletonList(exchangeRate));
        Mockito.verify(exchangeRateDtoConverter).convertToBaseCurrency(dto, filter);
    }

    @Test
    @DisplayName("saveOrUpdate() should save new object if old is not found")
    public void saveOrUpdateShouldSaveNewObjectIfOldIsNotFound() {
        Date newYear2020 = new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime();
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setDate(newYear2020);
        exchangeRate.setBaseCurrency(Currency.EUR);
        exchangeRate.setCurrency(Currency.USD);
        exchangeRate.setRate(1.3);

        when(exchangeRateRepository.findFirstByCurrencyAndDateLessThanEqualOrderByDateDesc(Currency.USD, newYear2020))
                .thenReturn(Optional.empty());
        when(exchangeRateRepository.save(exchangeRate))
                .thenReturn(exchangeRate);

        var result = exchangeRateService.saveOrUpdate(exchangeRate);

        assertNotNull(result);
        assertAll(
                "saveOrUpdate() should return valid object",
                () -> assertEquals(newYear2020, result.getDate()),
                () -> assertEquals(Currency.USD, result.getCurrency()),
                () -> assertEquals(Currency.EUR, result.getBaseCurrency()),
                () -> assertEquals(1.3, result.getRate())
        );
        Mockito.verify(exchangeRateRepository).save(exchangeRate);
    }

    @Test
    @DisplayName("saveOrUpdate() should save new object if old is found")
    public void saveOrUpdateShouldUpdateOldObjectIfFound() {
        Date newYear2020 = new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime();
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setDate(newYear2020);
        exchangeRate.setBaseCurrency(Currency.EUR);
        exchangeRate.setCurrency(Currency.USD);
        exchangeRate.setRate(1.3);

        ExchangeRate rateInDb = new ExchangeRate();
        rateInDb.setId(1L);
        rateInDb.setDate(newYear2020);
        rateInDb.setBaseCurrency(Currency.EUR);
        rateInDb.setCurrency(Currency.USD);
        rateInDb.setRate(1.22);

        ExchangeRate updatedRateInDb = new ExchangeRate();
        updatedRateInDb.setId(1L);
        updatedRateInDb.setDate(newYear2020);
        updatedRateInDb.setBaseCurrency(Currency.EUR);
        updatedRateInDb.setCurrency(Currency.USD);
        updatedRateInDb.setRate(1.3);

        when(exchangeRateRepository.findFirstByCurrencyAndDateLessThanEqualOrderByDateDesc(Currency.USD, newYear2020))
                .thenReturn(Optional.of(rateInDb));
        when(exchangeRateRepository.save(rateInDb))
                .thenReturn(updatedRateInDb);

        var result = exchangeRateService.saveOrUpdate(exchangeRate);

        assertNotNull(result);
        assertAll(
                "saveOrUpdate() should return valid object",
                () -> assertEquals(1, result.getId()),
                () -> assertEquals(newYear2020, result.getDate()),
                () -> assertEquals(Currency.USD, result.getCurrency()),
                () -> assertEquals(Currency.EUR, result.getBaseCurrency()),
                () -> assertEquals(1.3, result.getRate())
        );
        Mockito.verify(exchangeRateRepository).save(rateInDb);
    }

    @Test
    @DisplayName("count() should return Long object")
    public void countShouldReturnLong() {
        when(exchangeRateRepository.count()).thenReturn(1L);

        var result = exchangeRateService.count();

        assertNotNull(result);
        assertEquals(1L, result);
        verify(exchangeRateRepository).count();
    }

    @Test
    @DisplayName("writeEcbRatesToDb() should iterate over rates")
    public void writeEcbRatesToDbShouldIterateOverRates() {
        Date newYear2020 = new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime();
        EcbRatesByDate ratesByDate = new EcbRatesByDate();
        ratesByDate.setDate(newYear2020);
        ratesByDate.setRates(Arrays.asList(
                new EcbRate(Currency.USD, 1.3),
                new EcbRate(Currency.RUB, 999.9)
        ));
        EcbEnvelope ecbEnvelope = new EcbEnvelope();
        ecbEnvelope.setRates(Collections.singletonList(ratesByDate));

        exchangeRateService.writeEcbRatesToDb(ecbEnvelope);

        verify(exchangeRateRepository, times(2)).save(any(ExchangeRate.class));
    }

    @Test
    @DisplayName("getHistoryRates() with correct filter should return list of DTOs")
    public void getHistoryRates_withCorrectFilter() {
        Date newYear2020 = new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime();
        Date secondOfJanuary2020 = new GregorianCalendar(2020, Calendar.JANUARY, 2).getTime();
        ExchangeRate exchangeRate1 = new ExchangeRate();
        exchangeRate1.setDate(newYear2020);
        exchangeRate1.setBaseCurrency(Currency.EUR);
        exchangeRate1.setCurrency(Currency.USD);
        exchangeRate1.setRate(1.3);

        ExchangeRate exchangeRate2 = new ExchangeRate();
        exchangeRate2.setDate(newYear2020);
        exchangeRate2.setBaseCurrency(Currency.EUR);
        exchangeRate2.setCurrency(Currency.USD);
        exchangeRate2.setRate(1.4);

        ExchangeRateDto dto1 = new ExchangeRateDto();
        dto1.setDate(newYear2020);
        dto1.setBase(Currency.EUR);
        dto1.setRates(Collections.singletonMap(Currency.USD, BigDecimal.valueOf(1.3)));
        ExchangeRateDto dto2 = new ExchangeRateDto();
        dto1.setDate(secondOfJanuary2020);
        dto1.setBase(Currency.EUR);
        dto1.setRates(Collections.singletonMap(Currency.USD, BigDecimal.valueOf(1.4)));

        ExchangeRateHistoryFilter filter = new ExchangeRateHistoryFilter();
        filter.setStartDate(newYear2020);
        filter.setEndDate(secondOfJanuary2020);

        when(exchangeRateRepository.findByDateGreaterThanEqualAndDateLessThanEqualAndCurrencyIn(
                newYear2020, secondOfJanuary2020, new HashSet<>(Set.of(Currency.values()))))
                .thenReturn(List.of(exchangeRate1, exchangeRate2));
        when(exchangeRateMapper.entityListToDtoList(List.of(exchangeRate1, exchangeRate2)))
                .thenReturn(List.of(dto1, dto2));
        when(exchangeRateDtoConverter.convertToBaseCurrency(dto1, filter))
                .thenReturn(dto1);
        when(exchangeRateDtoConverter.convertToBaseCurrency(dto2, filter))
                .thenReturn(dto2);

        List<ExchangeRateDto> result = exchangeRateService.getHistoryRates(filter);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(dto1, result.get(0));
        Assertions.assertEquals(dto2, result.get(1));

        Mockito.verify(exchangeRateRepository).findByDateGreaterThanEqualAndDateLessThanEqualAndCurrencyIn(
                newYear2020, secondOfJanuary2020, new HashSet<>(Set.of(Currency.values())));
        Mockito.verify(exchangeRateMapper).entityListToDtoList(List.of(exchangeRate1, exchangeRate2));
        Mockito.verify(exchangeRateDtoConverter).convertToBaseCurrency(dto1, filter);
        Mockito.verify(exchangeRateDtoConverter).convertToBaseCurrency(dto2, filter);
    }
}
