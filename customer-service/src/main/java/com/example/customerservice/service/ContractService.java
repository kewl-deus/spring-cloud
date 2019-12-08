package com.example.customerservice.service;

import com.example.customerservice.exception.ContractNotFoundException;
import com.example.customerservice.model.valueobject.ContractNumber;
import com.example.customerservice.model.valueobject.CustomerIdentifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ContractService {

    public CustomerIdentifier getContractHolder(ContractNumber contractNumber){
        if (contractNumber == null) throw new ContractNotFoundException("Contract without number does not exist");

        return CustomerIdentifier.from(UUID.randomUUID().toString(), "intern");
    }
}
