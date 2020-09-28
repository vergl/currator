package io.github.vergl.currator.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration of integration with European Central Bank
 */
@Component
@ConfigurationProperties(prefix = "ecb")
public class EcbProperties {

    /**
     * European Central Bank's historical data URL.
     */
    private String historicalDataUrl;

    public String getHistoricalDataUrl() {
        return historicalDataUrl;
    }

    public void setHistoricalDataUrl(String historicalDataUrl) {
        this.historicalDataUrl = historicalDataUrl;
    }
}
