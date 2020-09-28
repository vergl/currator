package io.github.vergl.currator.domain.ecb;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EcbRatesByDate {

    @JacksonXmlProperty(isAttribute = true, localName = "time")
    private Date date;

    @JacksonXmlProperty(localName = "Cube")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<EcbRate> rates = new ArrayList<>();

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<EcbRate> getRates() {
        return rates;
    }

    public void setRates(List<EcbRate> rates) {
        this.rates = rates;
    }
}
