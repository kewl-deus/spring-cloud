package com.example.customerservice.controller;

import com.example.customerservice.event.CustomerRegistered;
import com.example.customerservice.event.EventBus;
import com.example.customerservice.event.ExistingCustomerRegistrationRequested;
import com.example.customerservice.exception.CustomerNotFoundException;
import com.example.customerservice.exception.InvalidRegistrationDataException;
import com.example.customerservice.hateoas.CustomerRepresentationModelAssembler;
import com.example.customerservice.model.dto.CustomerRegistrationData;
import com.example.customerservice.model.valueobject.CustomerIdentifier;
import com.example.customerservice.model.valueobject.Id;
import com.example.customerservice.model.valueobject.Name;
import com.example.customerservice.model.valueobject.ZipCode;
import com.example.customerservice.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(path = "/customers/registrations")
public class RegistrationController {

    @Autowired
    private EventBus eventBus;

    @Autowired
    private CustomerRepresentationModelAssembler representationModelAssembler;

    @Autowired
    private RegistrationService registrationService;

    @RequestMapping(method = RequestMethod.HEAD)
    public ResponseEntity isRegistered(@RequestParam final String identifierKey, @RequestParam final String identifierType) {
        CustomerIdentifier customerIdentifier = CustomerIdentifier.from(identifierKey, identifierType);
        long registrationCount = registrationService.getRegistrations(customerIdentifier).count();
        if (registrationCount > 0) {
            //TODO can we add any HATEOAS links here?
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping(consumes = {"application/vnd.registration.newcustomer+json;version=1"}, produces = {MediaType.APPLICATION_STREAM_JSON_VALUE})
    public ResponseEntity registerNewCustomer() {
        throw new UnsupportedOperationException();
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, "application/vnd.registration.existingcustomer+json;version=1"})
    public ResponseEntity registerExistingCustomer(@RequestHeader(value = "idToken") final CustomerIdentifier externalCustomerIdentifier,
                                                   @RequestBody final CustomerRegistrationData registrationData) throws ExecutionException, InterruptedException {
        ExistingCustomerRegistrationRequested regRequestedEvent = new ExistingCustomerRegistrationRequested(
                externalCustomerIdentifier,
                Id.from(registrationData.getCustomerId()),
                Name.of(registrationData.getLastname()),
                registrationData.getBirthDay(),
                ZipCode.of(registrationData.getZipCode()));

        AtomicReference<ResponseEntity> responseEntityReference = new AtomicReference<>();
        eventBus.register(CustomerRegistered.class, event -> {
            responseEntityReference.set(createResponse(event));
        }, 1);

        eventBus.send(regRequestedEvent);

        CompletableFuture<ResponseEntity> responseFuture = CompletableFuture
                .supplyAsync(() -> {
                    ResponseEntity responseEntity = null;
                    while (responseEntity == null) {
                        responseEntity = responseEntityReference.get();
                    }
                    return responseEntity;
                })
                .exceptionally(throwable -> {
                    if (throwable instanceof InvalidRegistrationDataException) {
                        return handleException((InvalidRegistrationDataException) throwable);
                    }
                    if (throwable instanceof CustomerNotFoundException) {
                        return handleException((CustomerNotFoundException) throwable);
                    }
                    return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
                })
                .completeOnTimeout(new ResponseEntity(HttpStatus.GATEWAY_TIMEOUT), 3, TimeUnit.SECONDS);

        return responseFuture.get();

        //wait for registration event
        //TODO how to return HEAD request in HATEOAS?
        //TODO do we need another method getRegistrationStatus() that queries events in order to show job progress?
        /*
        Link registrationStatusLink = new Link("/customers/registrations?identifierKey="
                + externalCustomerIdentifier.getKey() + "&identifierType=" + externalCustomerIdentifier.getType(), "registrations");
        return ResponseEntity.status(HttpStatus.ACCEPTED).location(URI.create(registrationStatusLink.getHref())).build();
         */
    }


    private ResponseEntity createResponse(CustomerRegistered customerRegisteredEvent) {
        String externalId = customerRegisteredEvent.getExternalIdentifier().getKey();
        Link customerLink;
        try {
            customerLink = linkTo(CustomerController.class, externalId).withRel("customer");
            //Link customerLink = linkTo(methodOn(CustomerController.class, "id").getCustomer("id")).withRel("customer");
            //URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path(customerLink.getHref()).build(Collections.emptyMap());
        } catch (Throwable ex) {
            customerLink = new Link("customers/" + externalId, "customer");
        }


        return ResponseEntity.status(HttpStatus.CREATED).location(customerLink.toUri()).build();
    }

    @ExceptionHandler({InvalidRegistrationDataException.class})
    public ResponseEntity<String> handleException(InvalidRegistrationDataException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({CustomerNotFoundException.class})
    public ResponseEntity<String> handleException(CustomerNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({TimeoutException.class, InterruptedException.class, ExecutionException.class})
    public ResponseEntity<String> handleException(TimeoutException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.GATEWAY_TIMEOUT);
    }
}


