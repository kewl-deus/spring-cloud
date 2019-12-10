package com.example.customerservice.model.dto;

import java.time.LocalDate;

public class CustomerRegistrationData {

    private Long customerId;
    private String lastname;
    private LocalDate birthDay;
    private String zipCode;

    public CustomerRegistrationData(Long customerId, String lastname, LocalDate birthDay, String zipCode) {
        this.customerId = customerId;
        this.lastname = lastname;
        this.birthDay = birthDay;
        this.zipCode = zipCode;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getLastname() {
        return lastname;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public String getZipCode() {
        return zipCode;
    }
}