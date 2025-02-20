package com.parkingmanagement.parkingmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ParkingmanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkingmanagementApplication.class, args);
	}

}
