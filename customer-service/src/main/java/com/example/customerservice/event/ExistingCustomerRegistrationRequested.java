package com.example.customerservice.event;

import com.example.customerservice.model.valueobject.CustomerIdentifier;
import com.example.customerservice.model.valueobject.Id;
import com.example.customerservice.model.valueobject.Name;
import com.example.customerservice.model.valueobject.ZipCode;

import java.time.LocalDate;

public class ExistingCustomerRegistrationRequested implements DomainEvent {

    private final CustomerIdentifier externalCustomerId;
    private final Id customerId;
    private final Name lastname;
    private final LocalDate birthDay;
    private final ZipCode zipCode;

    public ExistingCustomerRegistrationRequested(CustomerIdentifier externalCustomerId, Id customerId, Name lastname, LocalDate birthDay, ZipCode zipCode) {
        this.externalCustomerId = externalCustomerId;
        this.customerId = customerId;
        this.lastname = lastname;
        this.birthDay = birthDay;
        this.zipCode = zipCode;
    }

    public CustomerIdentifier getExternalCustomerId() {
        return externalCustomerId;
    }

    public Id getCustomerId() {
        return customerId;
    }

    public Name getLastname() {
        return lastname;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public ZipCode getZipCode() {
        return zipCode;
    }
}
