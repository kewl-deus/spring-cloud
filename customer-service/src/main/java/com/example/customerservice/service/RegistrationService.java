package com.example.customerservice.service;

import com.example.customerservice.model.entity.CustomerIndexRecord;
import com.example.customerservice.model.valueobject.CustomerIdentifier;
import com.example.customerservice.repository.CustomerIndex;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class RegistrationService {

    private final CustomerIndex repository;

    public RegistrationService(CustomerIndex repository) {
        this.repository = repository;
    }

    public CustomerIndexRecord register(CustomerIdentifier externalId, CustomerIdentifier internalId) {
        return repository.save(new CustomerIndexRecord(internalId, externalId));
    }

    public Stream<CustomerIdentifier> getRegistrations(CustomerIdentifier identifier) {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .filter(record -> record.getExternalIdentifier().equals(identifier) || record.getInternalIdentifier().equals(identifier))
                .map(record -> record.getExternalIdentifier().equals(identifier) ? record.getInternalIdentifier() : record.getExternalIdentifier());
    }
}
