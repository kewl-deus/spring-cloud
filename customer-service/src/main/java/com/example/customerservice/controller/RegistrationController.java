package com.example.customerservice.controller;

import com.example.customerservice.hateoas.CustomerRepresentationModelAssembler;
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
import reactor.bus.selector.Selectors;
import reactor.fn.Consumer;

import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
public class RegistrationController {

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private CommandFactory commandFactory;

    @Autowired
    private EventBus eventBus;

    @Autowired
    private CustomerRepresentationModelAssembler representationModelAssembler;

    @PostMapping(value = "/registrations", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity registerCustomer(@RequestHeader("idToken") CustomerIdentifier externalCustomerIdentifier,
                                           @RequestBody CustomerRegistrationData registrationData)
            throws InterruptedException, ExecutionException, TimeoutException {
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

        //wait for registration event

        final AtomicReference<CustomerRegistered> registeredEventHolder = new AtomicReference<>();
        Consumer<Event<CustomerRegistered>> eventConsumer = event -> {
            registeredEventHolder.set(event.getData());
        };

        eventBus.on(Selectors.type(CustomerRegistered.class), eventConsumer).cancelAfterUse();

        CompletableFuture<CustomerRegistered> promise = CompletableFuture.supplyAsync(() -> {
            CustomerRegistered event = null;
            while (event == null){
                event = registeredEventHolder.get();
            }
            return event;
        });

        String externalId = promise.get(1000, TimeUnit.MILLISECONDS).getExternalIdentifier().getKey();
        Link customerLink;
        try {
            customerLink = linkTo(CustomerController.class, externalId).withRel("customer");
            //Link customerLink = linkTo(methodOn(CustomerController.class, "id").getCustomer("id")).withRel("customer");
        } catch (Throwable ex) {
            customerLink = new Link("/customers/" + externalId, "customer");
        }

        return ResponseEntity.status(HttpStatus.CREATED).location(URI.create(customerLink.getHref())).build();
    }

    @ExceptionHandler({InvalidRegistrationDataException.class})
    public ResponseEntity<String> handleException(InvalidRegistrationDataException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ContractNotFoundException.class})
    public ResponseEntity<String> handleException(ContractNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({TimeoutException.class, InterruptedException.class, ExecutionException.class})
    public ResponseEntity<String> handleException(TimeoutException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.GATEWAY_TIMEOUT);
    }
}


