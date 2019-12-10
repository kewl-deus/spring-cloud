package com.example.customerservice.event.handler;

import com.example.customerservice.event.CustomerRegistered;
import com.example.customerservice.event.RegistrationDataValidated;
import com.example.customerservice.event.sourcing.EventBus;
import com.example.customerservice.model.entity.CustomerIndexRecord;
import com.example.customerservice.service.RegistrationService;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class RegistrationDataValidatedHandler implements Consumer<RegistrationDataValidated> {

    private final EventBus eventBus;

    private final RegistrationService registrationService;

    public RegistrationDataValidatedHandler(EventBus eventBus, RegistrationService registrationService) {
        this.eventBus = eventBus;
        this.eventBus.register(RegistrationDataValidated.class, this);
        this.registrationService = registrationService;
    }

    @Override
    public void accept(RegistrationDataValidated validatedEvent) {
        CustomerIndexRecord record = registrationService.register(validatedEvent.getExternalIdentifier(), validatedEvent.getInternalIdentifier());
        CustomerRegistered registeredEvent = new CustomerRegistered(record.getExternalIdentifier(), record.getInternalIdentifier());
        eventBus.send(registeredEvent);
    }
}
