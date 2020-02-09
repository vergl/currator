package io.github.vergl.currator.repository;

import io.github.vergl.currator.domain.ExchangeRate;
import io.github.vergl.currator.domain.enumeration.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    ExchangeRate findByDateAndCurrency(Date date, Currency currency);
}
