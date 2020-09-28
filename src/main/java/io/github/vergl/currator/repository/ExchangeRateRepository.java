package io.github.vergl.currator.repository;

import io.github.vergl.currator.domain.ExchangeRate;
import io.github.vergl.currator.domain.enumeration.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    /**
     * Search for the exchange rate of chosen currency and date
     * (if date is not found, choose the nearest previous one)
     *
     * @param currency Currency
     * @param date     Date
     * @return Exchange Rate or empty optional
     */
    Optional<ExchangeRate> findFirstByCurrencyAndDateLessThanEqualOrderByDateDesc(Currency currency, Date date);

    /**
     * Search for the exchange rate with chosen or nearest previous date.
     *
     * @param date Date
     * @return Exchange Rate or empty optional
     */
    Optional<ExchangeRate> findFirstByDateLessThanEqualOrderByDateDesc(Date date);

    /**
     * Search for all exchange rates for chosen date and currencies.
     *
     * @param date       Date
     * @param currencies List of currencies
     * @return List of Exchange Rates
     */
    List<ExchangeRate> findByDateAndCurrencyIn(Date date, Set<Currency> currencies);

    /**
     * Search for all exchange rates for chosen currencies and date interval
     *
     * @param startDate  Start date (including this date)
     * @param endDate    End date (including this date)
     * @param currencies List of currencies
     * @return List of Exchange Rates
     */
    List<ExchangeRate> findByDateGreaterThanEqualAndDateLessThanEqualAndCurrencyIn(Date startDate, Date endDate, Set<Currency> currencies);
}
