package com.example.customerservice.controller;

import com.example.customerservice.command.CommandFactory;
import com.example.customerservice.command.CommandGateway;
import com.example.customerservice.command.RegisterCustomerCommand;
import com.example.customerservice.event.CustomerEvent;
import com.example.customerservice.event.CustomerRegistered;
import com.example.customerservice.event.CustomerRegistrationRequested;
import com.example.customerservice.model.dto.CustomerRegistrationData;
import com.example.customerservice.model.valueobject.ContractNumber;
import com.example.customerservice.model.valueobject.CustomerIdentifier;
import com.example.customerservice.model.valueobject.Name;
import com.example.customerservice.model.valueobject.ZipCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.bus.registry.Registration;
import reactor.bus.selector.Selectors;
import reactor.fn.Consumer;
import reactor.fn.Function;

import java.net.URI;

@RestController
public class RegistrationController {

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private CommandFactory commandFactory;

    @Autowired
    private EventBus eventBus;

    @PostMapping(value = "/registrations", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity registerCustomer(@RequestHeader("idToken") CustomerIdentifier externalCustomerIdentifier,
                                           @RequestBody CustomerRegistrationData registrationData) {
        CustomerRegistrationRequested regRequestedEvent = new CustomerRegistrationRequested(
                externalCustomerIdentifier,
                ContractNumber.from(registrationData.getContractNumber()),
                Name.of(registrationData.getLastname()), registrationData.getBirthDay(), ZipCode.of(registrationData.getZipCode()));

        eventBus.notify(CustomerRegistrationRequested.class, new Event<>(regRequestedEvent));

        RegisterCustomerCommand cmd = commandFactory.createRegisterCustomerCommand(externalCustomerIdentifier,
                ContractNumber.from(registrationData.getContractNumber()),
                Name.of(registrationData.getLastname()),
                registrationData.getBirthDay(),
                ZipCode.of(registrationData.getZipCode()));

        commandGateway.send(cmd);

        Registration<?, Consumer<? extends Event<?>>> registration = eventBus.receive(Selectors.type(CustomerRegistered.class), (Event<CustomerRegistered> event) -> {
            CustomerRegistered regEvent = event.getData();
            return ResponseEntity.status(HttpStatus.CREATED).location(URI.create("/customers/" + regEvent.getExternalIdentifier().getKey())).build();
        }).cancelAfterUse();
        return null;
    }
}


