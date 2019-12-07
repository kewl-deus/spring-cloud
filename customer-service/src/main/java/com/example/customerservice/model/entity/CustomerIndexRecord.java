package com.example.customerservice.model.entity;

import com.example.customerservice.model.valueobject.CustomerIdentifier;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;

@Entity
public class CustomerIndexRecord {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private CustomerIdentifier internalIdentifier;

    @Embedded
    private CustomerIdentifier externalIdentifier;

    public CustomerIndexRecord(CustomerIdentifier internalIdentifier, CustomerIdentifier externalIdentifier) {
        this.internalIdentifier = internalIdentifier;
        this.externalIdentifier = externalIdentifier;
    }

    public CustomerIndexRecord() {
    }

    public Long getId() {
        return id;
    }

    public CustomerIdentifier getInternalIdentifier() {
        return internalIdentifier;
    }

    public CustomerIdentifier getExternalIdentifier() {
        return externalIdentifier;
    }
}
