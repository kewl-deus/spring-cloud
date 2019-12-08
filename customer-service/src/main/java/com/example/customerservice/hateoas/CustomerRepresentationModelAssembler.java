package com.example.customerservice.hateoas;

import com.example.customerservice.controller.RegistrationController;
import com.example.customerservice.model.aggregate.Customer;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class CustomerRepresentationModelAssembler implements RepresentationModelAssembler<Customer, EntityModel<Customer>> {

    @Override
    public EntityModel<Customer> toModel(Customer customer) {
        EntityModel<Customer> resource = new EntityModel<>(customer);
        resource.add(linkTo(RegistrationController.class).withRel("registration"));
        return resource;
    }
}
