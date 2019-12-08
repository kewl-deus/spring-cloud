package com.example.customerservice.controller;

import com.example.customerservice.model.aggregate.Customer;
import com.example.customerservice.model.valueobject.Id;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping(path = "/customers", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
public class CustomerController {


    //@GetMapping(path = "{id}")
    public Customer getCustomer(@PathVariable String externalId){
        return null;
    }
}