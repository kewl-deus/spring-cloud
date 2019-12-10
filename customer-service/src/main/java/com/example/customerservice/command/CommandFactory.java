package com.example.customerservice.command;

import com.example.customerservice.model.valueobject.CustomerIdentifier;
import com.example.customerservice.model.valueobject.Id;
import com.example.customerservice.model.valueobject.Name;
import com.example.customerservice.model.valueobject.ZipCode;
import com.example.customerservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CommandFactory {

    @Autowired
    private CustomerRepository customerRepository;


    public RegisterCustomerCommand createRegisterCustomerCommand(
            CustomerIdentifier externalCustomerId,
            Id customerId,
            Name lastname,
            LocalDate birthDay,
            ZipCode zipCode) {
        return new RegisterCustomerCommand(customerRepository, externalCustomerId, customerId, lastname, birthDay, zipCode);
    }
}
