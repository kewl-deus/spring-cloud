package com.example.customerservice.controller;

import com.example.customerservice.model.aggregate.Customer;
import com.example.customerservice.model.valueobject.CustomerIdentifier;
import com.example.customerservice.model.valueobject.IdentitfierType;
import com.example.customerservice.repository.CustomerRepository;
import com.example.customerservice.service.RegistrationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/customers", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final RegistrationService registrationService;

    public CustomerController(CustomerRepository customerRepository, RegistrationService registrationService) {
        this.customerRepository = customerRepository;
        this.registrationService = registrationService;
    }

    @GetMapping(path = "{keyType}/{key}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("keyType") String keyType, @PathVariable("key") String externalId){
        return registrationService
                .getRegistrations(CustomerIdentifier.from(externalId, keyType))
                .filter(r -> r.isOf(IdentitfierType.internal))
                .map(r -> Long.valueOf(r.getKey()))
                .map(id -> customerRepository.findById(id))
                .flatMap(Optional::stream)
                .findFirst()
                .map(c -> new ResponseEntity<>(c, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public List<Customer> getCustomers(@RequestParam("page") int pageNo, @RequestParam("size") int pageSize){
        Page<Customer> page = customerRepository.findAll(PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc("lastname"), Sort.Order.asc("firstname"))));
        return page.getContent();
    }
}