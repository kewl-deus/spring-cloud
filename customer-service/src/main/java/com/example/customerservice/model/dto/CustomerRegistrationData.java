package com.example.customerservice.model.dto;

import java.util.Date;

public class CustomerRegistrationData {

    private String contractNumber;
    private String lastname;
    private Date birthDay;
    private String zipCode;

    public CustomerRegistrationData(String contractNumber, String lastname, Date birthDay, String zipCode) {
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

    public Date getBirthDay() {
        return birthDay;
    }

    public String getZipCode() {
        return zipCode;
    }
}