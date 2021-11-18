package com.event.generator.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EventGeneratorProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventGeneratorProducerApplication.class, args);
	}

}
