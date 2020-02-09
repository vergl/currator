package io.github.vergl.currator.domain.ecb;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class EcbRatesByDate {
    @JacksonXmlProperty(isAttribute = true, localName = "time")
    private Date date;
    @JacksonXmlProperty(localName = "Cube")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<EcbRate> rates = new ArrayList<>();
}
