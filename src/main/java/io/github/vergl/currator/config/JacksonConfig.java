package io.github.vergl.currator.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper xmlMapper() {
        return Jackson2ObjectMapperBuilder.xml().build();
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return Jackson2ObjectMapperBuilder
                .json()
                .simpleDateFormat("yyyy-MM-dd")
                .build();
    }
}
