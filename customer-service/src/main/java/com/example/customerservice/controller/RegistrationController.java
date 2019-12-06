package com.example.customerservice.controller;

import com.example.customerservice.command.CommandGateway;
import com.example.customerservice.command.RegisterCustomerCommand;
import com.example.customerservice.event.CustomerRegistrationRequestedEvent;
import com.example.customerservice.model.dto.CustomerRegistrationData;
import com.example.customerservice.model.valueobject.ContractNumber;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class RegistrationController {

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private EventBus eventBus;

    @PostMapping(value = "/registrations", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity registerCustomer(@RequestHeader("X-customer-id") String customerId, @RequestBody CustomerRegistrationData registrationData) {
        CustomerRegistrationRequestedEvent event = new CustomerRegistrationRequestedEvent(customerId, ContractNumber.from(registrationData.getContractNumber()), registrationData.getLastname(), registrationData.getBirthDay(), registrationData.getZipCode());
        eventBus.post(event);
        RegisterCustomerCommand cmd = new RegisterCustomerCommand(customerId, ContractNumber.from(registrationData.getContractNumber()), registrationData.getLastname(), registrationData.getBirthDay(), registrationData.getZipCode());
        commandGateway.send(cmd);
        return ResponseEntity.status(HttpStatus.CREATED).location(URI.create("/customers/" + customerId)).build();
    }
}


