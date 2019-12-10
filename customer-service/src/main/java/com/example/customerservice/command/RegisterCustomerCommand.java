package com.example.customerservice.command;

import com.example.customerservice.event.CustomerRegistrationDataValidated;
import com.example.customerservice.event.DomainEvent;
import com.example.customerservice.exception.CustomerNotFoundException;
import com.example.customerservice.exception.InvalidRegistrationDataException;
import com.example.customerservice.model.aggregate.Customer;
import com.example.customerservice.model.valueobject.CustomerIdentifier;
import com.example.customerservice.model.valueobject.Id;
import com.example.customerservice.model.valueobject.Name;
import com.example.customerservice.model.valueobject.ZipCode;
import com.example.customerservice.repository.CustomerRepository;
import com.example.customerservice.service.CustomerService;

import java.time.LocalDate;
import java.util.Optional;

public class RegisterCustomerCommand implements Command {

    //services
    private final CustomerRepository customerRepository;

    //payload
    private final CustomerIdentifier externalCustomerId;
    private final Id customerId;
    private final Name lastname;
    private final LocalDate birthDay;
    private final ZipCode zipCode;


    public RegisterCustomerCommand(CustomerRepository customerRepository,
                                   CustomerIdentifier externalCustomerId,
                                   Id customerId,
                                   Name lastname,
                                   LocalDate birthDay,
                                   ZipCode zipCode) {
        this.customerRepository = customerRepository;
        this.externalCustomerId = externalCustomerId;
        this.customerId = customerId;
        this.lastname = lastname;
        this.birthDay = birthDay;
        this.zipCode = zipCode;
    }

    @Override
    public DomainEvent execute() {
        //check backend if contract is valid

        Optional<Customer> optionalCustomer = customerRepository.findById(customerId.getValue());

        if (optionalCustomer.isEmpty()){
            throw new InvalidRegistrationDataException("Customer does not exist", new CustomerNotFoundException(customerId.toString()));
        }
        return new CustomerRegistrationDataValidated(externalCustomerId, new CustomerIdentifier(customerId.getValue().toString(), "internal"));
    }
}
