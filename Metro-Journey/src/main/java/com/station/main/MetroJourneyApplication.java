package com.station.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.station")
@EnableJpaRepositories(basePackages = "com.station.persistence")
@EntityScan(basePackages = "com.station.entity")
public class MetroJourneyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MetroJourneyApplication.class, args);
	}

}
