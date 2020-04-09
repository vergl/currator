package io.github.vergl.currator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class CurratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurratorApplication.class, args);
	}

}
