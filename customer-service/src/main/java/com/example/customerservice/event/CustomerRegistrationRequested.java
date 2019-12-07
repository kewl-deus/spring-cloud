package com.example.customerservice.event;

import com.example.customerservice.model.valueobject.ContractNumber;
import com.example.customerservice.model.valueobject.CustomerIdentifier;
import com.example.customerservice.model.valueobject.Name;
import com.example.customerservice.model.valueobject.ZipCode;

import java.util.Date;

public class CustomerRegistrationRequested implements CustomerEvent {

    private final CustomerIdentifier externalCustomerId;
    private final ContractNumber contractNumber;
    private final Name lastname;
    private final Date birthDay;
    private final ZipCode zipCode;

    public CustomerRegistrationRequested(CustomerIdentifier externalCustomerId, ContractNumber contractNumber, Name lastname, Date birthDay, ZipCode zipCode) {
        this.externalCustomerId = externalCustomerId;
        this.contractNumber = contractNumber;
        this.lastname = lastname;
        this.birthDay = birthDay;
        this.zipCode = zipCode;
    }
}
