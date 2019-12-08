package com.example.customerservice.controller;

import com.example.customerservice.command.CommandFactory;
import com.example.customerservice.command.CommandGateway;
import com.example.customerservice.command.RegisterCustomerCommand;
import com.example.customerservice.event.CustomerRegistered;
import com.example.customerservice.event.CustomerRegistrationRequested;
import com.example.customerservice.exception.ContractNotFoundException;
import com.example.customerservice.exception.InvalidRegistrationDataException;
import com.example.customerservice.model.dto.CustomerRegistrationData;
import com.example.customerservice.model.valueobject.ContractNumber;
import com.example.customerservice.model.valueobject.CustomerIdentifier;
import com.example.customerservice.model.valueobject.Name;
import com.example.customerservice.model.valueobject.ZipCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.MethodLinkBuilderFactory;
import org.springframework.hateoas.core.DummyInvocationUtils;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.bus.registry.Registration;
import reactor.bus.selector.Selectors;
import reactor.fn.Consumer;

import java.net.URI;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

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
            String externalId = regEvent.getExternalIdentifier().getKey();
            Link customerLink;
            try {
                customerLink = linkTo(CustomerController.class, externalId).withRel("customer");
                //Link customerLink = linkTo(methodOn(CustomerController.class, "id").getCustomer("id")).withRel("customer");
            } catch (Throwable ex) {
                customerLink = new Link("/customers/" + externalId, "customer");
            }

            return ResponseEntity.status(HttpStatus.CREATED).location(URI.create(customerLink.getHref())).build();
        }).cancelAfterUse();

        /*
        eventBus.on(Selectors.type(CustomerRegistered.class)).subscribe(new Subscriber<Event<?>>() {

            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(Event<?> customerRegisteredEvent) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
        */
        return null;
    }

    @ExceptionHandler({InvalidRegistrationDataException.class})
    public ResponseEntity<String> handleInvalidRegistrationData(InvalidRegistrationDataException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ContractNotFoundException.class})
    public ResponseEntity<String> handleInvalidRegistrationData(ContractNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}


