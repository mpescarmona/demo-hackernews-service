package com.mpescarmona.api.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DemoHackerNewsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoHackerNewsServiceApplication.class, args);
	}

}
