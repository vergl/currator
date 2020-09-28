package io.github.vergl.currator.domain.ecb;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.ArrayList;
import java.util.List;

@JacksonXmlRootElement(namespace = "gesmes")
public class EcbEnvelope {

    @JacksonXmlProperty(namespace = "gesmes")
    private String subject;

    @JacksonXmlProperty(namespace = "gesmes", localName = "Sender")
    private EcbSender sender;

    @JacksonXmlProperty(localName = "Cube")
    private List<EcbRatesByDate> rates = new ArrayList<>();

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public EcbSender getSender() {
        return sender;
    }

    public void setSender(EcbSender sender) {
        this.sender = sender;
    }

    public List<EcbRatesByDate> getRates() {
        return rates;
    }

    public void setRates(List<EcbRatesByDate> rates) {
        this.rates = rates;
    }
}
