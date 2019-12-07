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
import reactor.fn.Consumer;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CustomerIndex implements Consumer<CustomerRegistrationDataValidated> {

    @Autowired
    private CustomerIndexRepository repository;

    @Autowired
    private EventBus eventBus;

    public void register(CustomerIdentifier externalId, CustomerIdentifier internalId) {
        repository.save(new CustomerIndexRecord(internalId, externalId));
    }

    public Set<CustomerIdentifier> getRegistrations(CustomerIdentifier identifier) {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .filter(record -> record.getExternalIdentifier().equals(identifier) || record.getInternalIdentifier().equals(identifier))
                .map(record -> record.getExternalIdentifier().equals(identifier) ? record.getInternalIdentifier() : record.getExternalIdentifier())
                .collect(Collectors.toSet());
    }

    @Override
    public void accept(CustomerRegistrationDataValidated validatedEvent) {
        register(validatedEvent.getExternalIdentifier(), validatedEvent.getInternalIdentifier());
        CustomerRegistered registeredEvent = new CustomerRegistered();
        eventBus.notify(CustomerRegistered.class.getSimpleName(), new Event<>(registeredEvent));
    }
}
