package com.example.customerservice.event;

import com.example.customerservice.model.valueobject.CustomerIdentifier;

public class CustomerRegistrationDataValidated implements DomainEvent {

    private final CustomerIdentifier externalIdentifier;
    private final CustomerIdentifier internalIdentifier;

    public CustomerRegistrationDataValidated(CustomerIdentifier externalIdentifier, CustomerIdentifier internalIdentifier) {
        this.externalIdentifier = externalIdentifier;
        this.internalIdentifier = internalIdentifier;
    }

    public CustomerIdentifier getExternalIdentifier() {
        return externalIdentifier;
    }

    public CustomerIdentifier getInternalIdentifier() {
        return internalIdentifier;
    }


}
