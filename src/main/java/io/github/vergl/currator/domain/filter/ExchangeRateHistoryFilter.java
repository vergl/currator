package io.github.vergl.currator.domain.filter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExchangeRateHistoryFilter extends ExchangeRateBaseFilter {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate = new GregorianCalendar(1999, Calendar.JANUARY, 4).getTime();

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate = new Date();
}
