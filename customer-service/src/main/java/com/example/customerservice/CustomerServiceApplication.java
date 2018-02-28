package com.example.customerservice;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Collection;
import java.util.stream.Stream;

@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner dummyDataGenerator(CustomerRepository customerRepository){
        return args -> {
            String[] customers = "James Bond, Max Mustermann, Karl Müller, Ethan Hunt, Indiana Jones, Guybrush Threepwood".split(", ");
            Stream.of(customers)
                    .map(s -> s.split(" "))
                    .map(names -> new Customer(names[0], names[1]))
                    .forEach(customerRepository::save);
        };
    }

}

@RepositoryRestResource
interface CustomerRepository extends JpaRepository<Customer, Long>{
    @RestResource(path = "by-name")
    Customer findByFullName(@Param("firstname") String firstname, @Param("lastname") String lastname);
}

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
class Customer {

    @Id
    @GeneratedValue
    private Long id;

    private String firstname;
    private String lastname;


    /*
    private String street;
    private String zipCode;
    private String city;
    */

    public Customer(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }
}