package io.github.vergl.currator.domain.ecb;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JacksonXmlRootElement(namespace = "gesmes")
public class EcbEnvelope {
    @JacksonXmlProperty(namespace = "gesmes")
    private String subject;
    @JacksonXmlProperty(namespace = "gesmes", localName = "Sender")
    private EcbSender sender;
    @JacksonXmlProperty(localName = "Cube")
    private List<EcbRatesByDate> rates = new ArrayList<>();
}
