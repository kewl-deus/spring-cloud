package com.example.customerservice.model.valueobject;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class Address {

    private String street;

    private String houseNo;

    @Embedded
    private ZipCode zipCode;

    private String city;
}
