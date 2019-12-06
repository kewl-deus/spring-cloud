package com.example.customerservice.command;

import com.example.customerservice.backend.ContractBackend;
import com.example.customerservice.event.CustomerRegistrationDataValidatedEvent;
import com.example.customerservice.event.Event;
import com.example.customerservice.model.valueobject.ContractNumber;
import com.example.customerservice.model.valueobject.CustomerIdentifier;

import java.util.Date;

public class RegisterCustomerCommand implements Command {

    private ContractBackend contractBackend;

    private String externalCustomerId;
    private ContractNumber contractNumber;
    private String lastname;
    private Date birthDay;
    private String zipCode;

    public RegisterCustomerCommand(String externalCustomerId, ContractNumber contractNumber, String lastname, Date birthDay, String zipCode) {
        this.externalCustomerId = externalCustomerId;
        this.contractNumber = contractNumber;
        this.lastname = lastname;
        this.birthDay = birthDay;
        this.zipCode = zipCode;
    }


    @Override
    public Event execute() {
        //check backend if contract is valid
        CustomerIdentifier contractHolderId = contractBackend.getContractHolder(contractNumber);
        return new CustomerRegistrationDataValidatedEvent(CustomerIdentifier.from(externalCustomerId, "external"), contractHolderId);
    }
}
