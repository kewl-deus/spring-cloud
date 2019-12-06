package com.example.customerservice;

import com.example.customerservice.command.RegisterCustomerCommand;
import com.example.customerservice.engine.CommandGateway;
import com.example.customerservice.model.valueobject.ContractNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class RegistrationController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping("/registrations")
    public void registerCustomer(@RequestHeader("X-customer-id") String customerId, @RequestBody CustomerRegistrationData registrationData) {
        RegisterCustomerCommand cmd = new RegisterCustomerCommand(customerId, ContractNumber.from(registrationData.getContractNumber()), registrationData.getLastname(), registrationData.getBirthDay(), registrationData.getZipCode());
        commandGateway.send(cmd);

    }
}


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