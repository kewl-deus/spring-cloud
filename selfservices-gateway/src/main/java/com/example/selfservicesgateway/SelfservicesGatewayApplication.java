package com.example.selfservicesgateway;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@EnableCircuitBreaker
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class SelfservicesGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SelfservicesGatewayApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}

@RestController
@RequestMapping("/customers")
class CustomerApiGatewayRestController {

	@Autowired
	private RestTemplate restTemplate;

	@SuppressWarnings("unused")
	public Collection<String> getCustomerNamesFallback(){
		return new ArrayList<>();
	}


	@HystrixCommand(fallbackMethod = "getCustomerNamesFallback")
	@GetMapping("/names")
	public Collection<String> getCustomerNames(){
		ParameterizedTypeReference<Resources<Customer>> customerPtr = new ParameterizedTypeReference<Resources<Customer>>() {};
		ResponseEntity<Resources<Customer>> entity = this.restTemplate.exchange("http://customer-service/customers", HttpMethod.GET, null, customerPtr);
		return entity
				.getBody()
				.getContent()
				.stream()
				.map(c -> c.getLastname() + ", " + c.getFirstname())
				.collect(Collectors.toList());
	}
}

class Customer {
	private String firstname;
	private String lastname;

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	@Override
	public String toString() {
		return "Customer{" +
				"firstname='" + firstname + '\'' +
				", lastname='" + lastname + '\'' +
				'}';
	}
}