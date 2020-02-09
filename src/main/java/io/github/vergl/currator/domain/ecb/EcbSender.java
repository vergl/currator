package io.github.vergl.currator.domain.ecb;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class EcbSender {
    @JacksonXmlProperty(namespace = "gesmes")
    private String name;
}
