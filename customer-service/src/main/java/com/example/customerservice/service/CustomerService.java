package com.example.customerservice.service;

import com.example.customerservice.model.aggregate.Customer;
import com.example.customerservice.model.valueobject.Id;
import com.example.customerservice.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(final CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public boolean isExistingCustomer(final Id customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId.getValue());
        return optionalCustomer.isPresent();
    }

}
