package com.example.customerservice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Collection;
import java.util.stream.Stream;

@EnableDiscoveryClient
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
                    .forEach(c -> {
                        customerRepository.save(c);
                        System.out.println("Saved " + c);
                    });
        };
    }

}

@RefreshScope
@RestController
class GreetingRestController {

    @Value("${greeting.phrase}")
    private String greetingPhrase;

    @GetMapping("/greeting/{customerId}")
    public String greeting(@PathVariable("customerId") Long customerId){
        System.out.println("Greeting " + customerId);
        return greetingPhrase + " " + customerId;
    }
}

@RepositoryRestResource
interface CustomerRepository extends JpaRepository<Customer, Long>{
    @RestResource(path = "by-fullname")
    Customer getByFirstnameAndLastname(@Param("firstname") String firstname, @Param("lastname") String lastname);
}

@Entity
/*
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
*/
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

    public Customer() {
    }

    public Customer(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}