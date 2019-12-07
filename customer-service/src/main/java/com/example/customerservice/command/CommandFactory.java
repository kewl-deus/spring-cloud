package com.example.customerservice.command;

import com.example.customerservice.model.valueobject.ContractNumber;
import com.example.customerservice.model.valueobject.CustomerIdentifier;
import com.example.customerservice.model.valueobject.Name;
import com.example.customerservice.model.valueobject.ZipCode;
import com.example.customerservice.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CommandFactory {

    @Autowired
    private ContractService contractService;


    public RegisterCustomerCommand createRegisterCustomerCommand(
            CustomerIdentifier externalCustomerId,
            ContractNumber contractNumber,
            Name lastname, Date birthDay,
            ZipCode zipCode) {

        return new RegisterCustomerCommand(contractService, externalCustomerId, contractNumber, lastname, birthDay, zipCode);
    }
}
