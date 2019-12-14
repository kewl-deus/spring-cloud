package com.example.customerservice.event;

import com.example.customerservice.model.valueobject.CustomerIdentifier;
import com.example.customerservice.model.valueobject.Id;
import com.example.customerservice.model.valueobject.Name;
import com.example.customerservice.model.valueobject.ZipCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@Getter
@AllArgsConstructor
public class ExistingCustomerRegistrationRequested implements DomainEvent {

    private final CustomerIdentifier externalCustomerId;
    private final Id customerId;
    private final Name lastname;
    private final LocalDate birthDay;
    private final ZipCode zipCode;

}
