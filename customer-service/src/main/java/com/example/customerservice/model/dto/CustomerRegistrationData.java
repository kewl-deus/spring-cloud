package com.example.customerservice.model.dto;

import java.time.LocalDate;

public class CustomerRegistrationData {

    private String contractNumber;
    private String lastname;
    private LocalDate birthDay;
    private String zipCode;

    public CustomerRegistrationData(String contractNumber, String lastname, LocalDate birthDay, String zipCode) {
        this.contractNumber = contractNumber;
        this.lastname = lastname;
        this.birthDay = birthDay;
        this.zipCode = zipCode;
    }

    public String getContractNumber() {
        return contractNumber;
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