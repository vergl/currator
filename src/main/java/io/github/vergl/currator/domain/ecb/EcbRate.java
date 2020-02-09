package io.github.vergl.currator.domain.ecb;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.github.vergl.currator.domain.enumeration.Currency;
import lombok.Data;

@Data
public class EcbRate {
    @JacksonXmlProperty(isAttribute = true)
    private Currency currency;
    @JacksonXmlProperty(isAttribute = true)
    private Double rate;
}
