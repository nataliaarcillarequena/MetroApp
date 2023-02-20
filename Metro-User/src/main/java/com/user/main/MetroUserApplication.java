package com.user.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.user")
@EntityScan(basePackages = "com.user.entity")
@EnableJpaRepositories(basePackages = "com.user.persistence")
public class MetroUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(MetroUserApplication.class, args);
	}

}
