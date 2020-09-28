package io.github.vergl.currator.domain.filter;

import io.swagger.annotations.ApiParam;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ExchangeRateDateFilter extends ExchangeRateBaseFilter {

    @ApiParam(value = "Date", format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date = new Date();

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
