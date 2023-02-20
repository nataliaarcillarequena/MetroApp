package com.metro.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = "com.metro")
@EnableJpaRepositories(basePackages = "com.metro.persistence")
@EntityScan(basePackages = "com.metro.entity")
public class MetroUserJourneyMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(MetroUserJourneyMvcApplication.class, args);
	}

	@Bean
	public RestTemplate gettemplate() {
		return new RestTemplate();
	}
	
}
