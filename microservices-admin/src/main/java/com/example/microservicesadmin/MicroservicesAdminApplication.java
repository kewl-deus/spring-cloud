package com.example.microservicesadmin;

import de.codecentric.boot.admin.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableAdminServer
@EnableDiscoveryClient
@SpringBootApplication
public class MicroservicesAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicesAdminApplication.class, args);
	}
}
