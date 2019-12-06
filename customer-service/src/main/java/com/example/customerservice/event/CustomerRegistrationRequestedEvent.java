package com.example.customerservice.event;

import com.example.customerservice.model.valueobject.ContractNumber;

import java.util.Date;

public class CustomerRegistrationRequestedEvent implements Event {

    private final String externalCustomerId;
    private final ContractNumber contractNumber;
    private final String lastname;
    private final Date birthDay;
    private final String zipCode;

    public CustomerRegistrationRequestedEvent(String externalCustomerId, ContractNumber contractNumber, String lastname, Date birthDay, String zipCode) {
        this.externalCustomerId = externalCustomerId;
        this.contractNumber = contractNumber;
        this.lastname = lastname;
        this.birthDay = birthDay;
        this.zipCode = zipCode;
    }
}
