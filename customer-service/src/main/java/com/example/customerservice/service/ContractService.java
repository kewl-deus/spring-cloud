package com.example.customerservice.service;

import com.example.customerservice.model.valueobject.ContractNumber;
import com.example.customerservice.model.valueobject.CustomerIdentifier;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Service
public class ContractService {

    public CustomerIdentifier getContractHolder(ContractNumber contractNumber){
        throw new NotImplementedException();
    }
}
