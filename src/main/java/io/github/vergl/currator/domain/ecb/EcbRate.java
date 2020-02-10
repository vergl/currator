package io.github.vergl.currator.domain.ecb;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.github.vergl.currator.domain.enumeration.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EcbRate {
    @JacksonXmlProperty(isAttribute = true)
    private Currency currency;
    @JacksonXmlProperty(isAttribute = true)
    private Double rate;
}
