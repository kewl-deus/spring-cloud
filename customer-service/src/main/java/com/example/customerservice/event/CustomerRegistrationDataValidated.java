package com.example.customerservice.event;

import com.example.customerservice.model.valueobject.CustomerIdentifier;

import java.util.Objects;

public class CustomerRegistrationDataValidated implements CustomerEvent {

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

    @Override
    public String toString() {
        return "CustomerRegistrationDataValidatedEvent{" +
                "externalId=" + externalIdentifier +
                ", internalId=" + internalIdentifier +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerRegistrationDataValidated that = (CustomerRegistrationDataValidated) o;
        return Objects.equals(externalIdentifier, that.externalIdentifier) &&
                Objects.equals(internalIdentifier, that.internalIdentifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(externalIdentifier, internalIdentifier);
    }
}
