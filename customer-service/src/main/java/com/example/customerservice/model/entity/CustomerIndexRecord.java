package com.example.customerservice.model.entity;

import com.example.customerservice.model.valueobject.CustomerIdentifier;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity()
@Table(name = "customerindex")
public class CustomerIndexRecord {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    @Column(nullable = false)
    @AttributeOverrides({
            @AttributeOverride(name = "key", column = @Column(name = "internal_key")),
            @AttributeOverride(name = "type", column = @Column(name = "internal_type"))})
    private CustomerIdentifier internalIdentifier;

    @Embedded
    @Column(nullable = false)
    @AttributeOverrides({
            @AttributeOverride(name = "key", column = @Column(name = "external_key")),
            @AttributeOverride(name = "type", column = @Column(name = "external_type"))})
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
