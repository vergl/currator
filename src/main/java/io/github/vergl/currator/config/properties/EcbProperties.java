package io.github.vergl.currator.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "ecb")
public class EcbProperties {

    /**
     * European Central Bank's historical data URL.
     */
    private String historicalDataUrl;
}