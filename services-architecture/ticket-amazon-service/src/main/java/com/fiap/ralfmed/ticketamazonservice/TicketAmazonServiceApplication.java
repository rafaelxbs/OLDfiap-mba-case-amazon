package com.fiap.ralfmed.ticketamazonservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
@SpringBootApplication
@EnableCircuitBreaker
public class TicketAmazonServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketAmazonServiceApplication.class, args);
	}

}
