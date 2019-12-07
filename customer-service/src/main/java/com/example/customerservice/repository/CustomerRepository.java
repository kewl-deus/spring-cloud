package com.example.customerservice.repository;

import com.example.customerservice.model.aggregate.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @RestResource(path = "by-fullname")
    Customer getByFirstnameAndLastname(@Param("firstname") String firstname, @Param("lastname") String lastname);
}
