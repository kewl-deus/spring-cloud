package com.example.customerservice.model.valueobject;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Getter
@Embeddable
public class Address {

    private String street;

    private String houseNo;

    @Embedded
    private ZipCode zipCode;

    private String city;
}
