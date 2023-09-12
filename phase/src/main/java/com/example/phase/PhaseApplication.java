package com.example.phase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PhaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhaseApplication.class, args);
	}

}
