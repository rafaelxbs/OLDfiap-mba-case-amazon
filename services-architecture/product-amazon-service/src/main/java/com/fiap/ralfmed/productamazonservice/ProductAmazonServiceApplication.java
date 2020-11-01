package com.fiap.ralfmed.productamazonservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProductAmazonServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductAmazonServiceApplication.class, args);
	}

}
