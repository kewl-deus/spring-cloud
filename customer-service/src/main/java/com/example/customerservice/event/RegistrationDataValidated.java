package com.example.customerservice.event;

import com.example.customerservice.model.valueobject.CustomerIdentifier;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class RegistrationDataValidated implements DomainEvent {

    private final CustomerIdentifier externalIdentifier;
    private final CustomerIdentifier internalIdentifier;


}
