package io.github.vergl.currator.domain.filter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExchangeRateDateFilter extends ExchangeRateBaseFilter {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date = new Date();
}
