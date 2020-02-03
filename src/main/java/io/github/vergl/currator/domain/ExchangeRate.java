package io.github.vergl.currator.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "EXCHANGE_RATES")
public class ExchangeRate {
    @Id
    @GeneratedValue
    private Long id;
    private Date date;
    private String baseCurrency;
    private String currency;
    private Double rate;
}
