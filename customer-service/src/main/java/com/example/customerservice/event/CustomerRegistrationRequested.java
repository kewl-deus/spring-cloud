package com.example.customerservice.event;

import com.example.customerservice.model.valueobject.CustomerIdentifier;
import com.example.customerservice.model.valueobject.Id;
import com.example.customerservice.model.valueobject.Name;
import com.example.customerservice.model.valueobject.ZipCode;

import java.time.LocalDate;

public class CustomerRegistrationRequested implements DomainEvent {

    private final CustomerIdentifier externalCustomerId;
    private final Id customerId;
    private final Name lastname;
    private final LocalDate birthDay;
    private final ZipCode zipCode;

    public CustomerRegistrationRequested(CustomerIdentifier externalCustomerId, Id customerId, Name lastname, LocalDate birthDay, ZipCode zipCode) {
        this.externalCustomerId = externalCustomerId;
        this.customerId = customerId;
        this.lastname = lastname;
        this.birthDay = birthDay;
        this.zipCode = zipCode;
    }
}
