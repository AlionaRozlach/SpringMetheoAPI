package com.example.SpringMetheoAPI;

import com.example.SpringMetheoAPI.simulator.MeteoSimulator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringMetheoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMetheoApiApplication.class, args);
	}
}
