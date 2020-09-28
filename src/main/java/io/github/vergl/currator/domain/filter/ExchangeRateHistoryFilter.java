package io.github.vergl.currator.domain.filter;

import io.swagger.annotations.ApiParam;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ExchangeRateHistoryFilter extends ExchangeRateBaseFilter {

    @ApiParam(value = "Start date", format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate = new GregorianCalendar(1999, Calendar.JANUARY, 4).getTime();

    @ApiParam(value = "End date", format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate = new Date();

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
