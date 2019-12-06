package com.example.customerservice.controller;

import com.example.customerservice.CustomerRepository;
import com.example.customerservice.model.aggregate.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.EntityNotFoundException;

@RefreshScope
@RestController
class GreetingController {

    @Autowired
    CustomerRepository customerRepository;

    @Value("${greeting.phrase}")
    private String greetingPhrase;

    @GetMapping("/greeting/{customerId}")
    public String greeting(@PathVariable("customerId") Long customerId){
        try {
            Customer customer = customerRepository.getOne(customerId);
            return greetingPhrase + " " + customer.getFirstname() + " " + customer.getLastname();
        } catch (EntityNotFoundException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }
}