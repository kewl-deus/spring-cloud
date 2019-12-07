package com.example.customerservice.service;

import com.example.customerservice.event.CustomerRegistered;
import com.example.customerservice.event.CustomerRegistrationDataValidated;
import com.example.customerservice.model.entity.CustomerIndexRecord;
import com.example.customerservice.model.valueobject.CustomerIdentifier;
import com.example.customerservice.repository.CustomerIndexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.bus.selector.Selectors;
import reactor.fn.Consumer;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CustomerIndex implements Consumer<CustomerRegistrationDataValidated> {

    private CustomerIndexRepository repository;

    private EventBus eventBus;

    public CustomerIndex(CustomerIndexRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
        this.eventBus.on(Selectors.type(CustomerRegistrationDataValidated.class), this);
    }

    public CustomerIndexRecord register(CustomerIdentifier externalId, CustomerIdentifier internalId) {
        return repository.save(new CustomerIndexRecord(internalId, externalId));
    }

    public Set<CustomerIdentifier> getRegistrations(CustomerIdentifier identifier) {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .filter(record -> record.getExternalIdentifier().equals(identifier) || record.getInternalIdentifier().equals(identifier))
                .map(record -> record.getExternalIdentifier().equals(identifier) ? record.getInternalIdentifier() : record.getExternalIdentifier())
                .collect(Collectors.toSet());
    }

    @Override
    public void accept(CustomerRegistrationDataValidated validatedEvent) {
        CustomerIndexRecord record = register(validatedEvent.getExternalIdentifier(), validatedEvent.getInternalIdentifier());
        CustomerRegistered registeredEvent = new CustomerRegistered(record.getExternalIdentifier(), record.getInternalIdentifier());
        eventBus.notify(CustomerRegistered.class, Event.wrap(registeredEvent));
    }
}
