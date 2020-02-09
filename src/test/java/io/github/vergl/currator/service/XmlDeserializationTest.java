package io.github.vergl.currator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.github.vergl.currator.domain.ecb.EcbEnvelope;
import io.github.vergl.currator.domain.ecb.EcbRatesByDate;
import io.github.vergl.currator.domain.ecb.EcbRate;
import io.github.vergl.currator.domain.ecb.EcbSender;
import io.github.vergl.currator.domain.enumeration.Currency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class XmlDeserializationTest {

    @Test
    @DisplayName("Jackson should deserialize XML")
    public void testMapper() throws IOException {
        ObjectMapper mapper = new XmlMapper();

        File xmlData = new ClassPathResource("exampledata.xml").getFile();
        EcbEnvelope result = mapper.readValue(xmlData, EcbEnvelope.class);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Reference rates", result.getSubject());

        EcbSender sender = result.getSender();
        Assertions.assertNotNull(sender);
        Assertions.assertEquals("European Central Bank", sender.getName());

        List<EcbRatesByDate> ratesByDates = result.getRates();
        Assertions.assertNotNull(ratesByDates);
        Assertions.assertEquals(1, ratesByDates.size());

        EcbRatesByDate ratesByCurrentDate = ratesByDates.get(0);
        Assertions.assertNotNull(ratesByCurrentDate);

        Date rateDate = ratesByCurrentDate.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        Assertions.assertEquals("2020-02-07", sdf.format(rateDate));

        List<EcbRate> rates = ratesByCurrentDate.getRates();
        Assertions.assertNotNull(rates);
        Assertions.assertEquals(32, rates.size());

        EcbRate usdRate = rates.get(0);
        Assertions.assertEquals(Currency.USD, usdRate.getCurrency());
        Assertions.assertEquals(1.0969, usdRate.getRate());
    }
}
