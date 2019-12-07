package com.example.customerservice.command;

import com.example.customerservice.event.CustomerRegistrationDataValidated;
import com.example.customerservice.event.CustomerEvent;
import com.example.customerservice.exception.InvalidRegistrationDataException;
import com.example.customerservice.model.valueobject.ContractNumber;
import com.example.customerservice.model.valueobject.CustomerIdentifier;
import com.example.customerservice.model.valueobject.Name;
import com.example.customerservice.model.valueobject.ZipCode;
import com.example.customerservice.service.ContractService;

import java.util.Date;

public class RegisterCustomerCommand implements Command {

    //services
    private final ContractService contractService;

    //payload
    private final CustomerIdentifier externalCustomerId;
    private final ContractNumber contractNumber;
    private final Name lastname;
    private final Date birthDay;
    private final ZipCode zipCode;


    public RegisterCustomerCommand(ContractService contractService,
                                   CustomerIdentifier externalCustomerId,
                                   ContractNumber contractNumber,
                                   Name lastname, Date birthDay,
                                   ZipCode zipCode) {
        this.contractService = contractService;
        this.externalCustomerId = externalCustomerId;
        this.contractNumber = contractNumber;
        this.lastname = lastname;
        this.birthDay = birthDay;
        this.zipCode = zipCode;
    }

    @Override
    public CustomerEvent execute() {
        //check backend if contract is valid
        CustomerIdentifier contractHolderId = contractService.getContractHolder(contractNumber);
        if (contractHolderId == null){
            throw new InvalidRegistrationDataException("Could not find holder for " + contractNumber);
        }
        return new CustomerRegistrationDataValidated(externalCustomerId, contractHolderId);
    }
}
