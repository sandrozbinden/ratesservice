package com.sandrozbinden;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RatesserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RatesserviceApplication.class, args);
	}
}
