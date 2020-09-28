package io.github.vergl.currator.domain;

import io.github.vergl.currator.domain.enumeration.Currency;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(Currency baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
                "id=" + id +
                ", date=" + date +
                ", baseCurrency=" + baseCurrency +
                ", currency=" + currency +
                ", rate=" + rate +
                '}';
    }
}
