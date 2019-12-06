package com.example.customerservice.backend;

import com.example.customerservice.model.valueobject.CustomerIdentifier;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Component
public class BusinessPartnerBackend {

    public void setOnlineFlag(CustomerIdentifier customerId){
        throw new NotImplementedException();
    }
}
