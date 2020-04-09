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

    Optional<ExchangeRate> findFirstByCurrencyAndDateLessThanEqualOrderByDateDesc(Currency currency, Date date);

    Optional<ExchangeRate> findFirstByDateLessThanEqualOrderByDateDesc(Date date);

    List<ExchangeRate> findByDateAndCurrencyIn(Date date, Set<Currency> currencies);

    List<ExchangeRate> findByDateGreaterThanEqualAndDateLessThanEqualAndCurrencyIn(Date startDate, Date endDate, Set<Currency> currencies);
}
