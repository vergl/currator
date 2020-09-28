package io.github.vergl.currator.domain.ecb;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.github.vergl.currator.domain.enumeration.Currency;

public class EcbRate {

    @JacksonXmlProperty(isAttribute = true)
    private Currency currency;

    @JacksonXmlProperty(isAttribute = true)
    private Double rate;

    public EcbRate() {

    }

    public EcbRate(Currency currency, Double rate) {
        this.currency = currency;
        this.rate = rate;
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
}
