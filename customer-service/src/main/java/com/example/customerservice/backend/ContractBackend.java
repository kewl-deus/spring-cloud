package com.example.customerservice.backend;

import com.example.customerservice.model.valueobject.ContractNumber;
import com.example.customerservice.model.valueobject.CustomerIdentifier;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Component
public class ContractBackend {

    public CustomerIdentifier getContractHolder(ContractNumber contractNumber){
        throw new NotImplementedException();
    }
}
