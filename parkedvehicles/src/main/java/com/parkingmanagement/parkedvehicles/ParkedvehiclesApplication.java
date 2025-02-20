package com.parkingmanagement.parkedvehicles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ParkedvehiclesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkedvehiclesApplication.class, args);
	}

}
