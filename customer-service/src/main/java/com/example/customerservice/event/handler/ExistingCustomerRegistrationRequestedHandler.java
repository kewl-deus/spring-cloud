package com.example.customerservice.event.handler;

import com.example.customerservice.event.ExistingCustomerRegistrationRequested;
import com.example.customerservice.event.RegistrationDataValidated;
import com.example.customerservice.event.sourcing.EventBus;
import com.example.customerservice.exception.CustomerNotFoundException;
import com.example.customerservice.exception.InvalidRegistrationDataException;
import com.example.customerservice.model.valueobject.CustomerIdentifier;
import com.example.customerservice.model.valueobject.Id;
import com.example.customerservice.model.valueobject.IdentitfierType;
import com.example.customerservice.service.CustomerService;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class ExistingCustomerRegistrationRequestedHandler implements Consumer<ExistingCustomerRegistrationRequested> {

    private final EventBus eventBus;

    private final CustomerService customerService;

    public ExistingCustomerRegistrationRequestedHandler(EventBus eventBus, CustomerService customerService) {
        this.eventBus = eventBus;
        this.eventBus.register(ExistingCustomerRegistrationRequested.class, this);
        this.customerService = customerService;
    }

    @Override
    public void accept(final ExistingCustomerRegistrationRequested event) {
        Id customerId = event.getCustomerId();

        if (! customerService.isExistingCustomer(customerId)){
            throw new InvalidRegistrationDataException("Customer does not exist", new CustomerNotFoundException(customerId.toString()));
        }
        RegistrationDataValidated outEvent = new RegistrationDataValidated(event.getExternalCustomerId(), CustomerIdentifier.from(customerId.getValue().toString(), IdentitfierType.internal));
        eventBus.send(outEvent);
    }
}
