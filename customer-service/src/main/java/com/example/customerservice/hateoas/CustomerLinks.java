package com.example.customerservice.hateoas;

import com.example.customerservice.controller.CustomerController;
import com.example.customerservice.model.aggregate.Customer;
import com.example.customerservice.model.valueobject.CustomerIdentifier;
import lombok.Getter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.TypedEntityLinks;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerLinks {

    private final TypedEntityLinks<Customer> customerLinks;

    CustomerLinks(EntityLinks entityLinks) {
        Assert.notNull(entityLinks, "EntityLinks must not be null!");
        this.customerLinks = entityLinks.forType(Customer::getId);
    }

    public Link getCustomerLink(Customer customer) {
        return customerLinks.linkToItemResource(customer);
    }

    public Link getCustomerLink(CustomerIdentifier customerIdentifier) {
        return linkTo(methodOn(CustomerController.class).getCustomer(customerIdentifier.getType(), customerIdentifier.getKey())).withSelfRel();
    }
}
