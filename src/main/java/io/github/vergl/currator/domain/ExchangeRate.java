package io.github.vergl.currator.domain;

import io.github.vergl.currator.domain.enumeration.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "EXCHANGE_RATES",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"BASE_CURRENCY", "CURRENCY", "DATE"}))
public class ExchangeRate {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE")
    private Date date;

    @Enumerated(EnumType.STRING)
    @Column(name = "BASE_CURRENCY", length = 3)
    private Currency baseCurrency = Currency.EUR;

    @Enumerated(EnumType.STRING)
    @Column(name = "CURRENCY", length = 3)
    private Currency currency;

    @Column(name = "RATE")
    private Double rate;
}
