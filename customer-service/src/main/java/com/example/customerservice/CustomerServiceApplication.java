package com.example.customerservice;

import com.example.customerservice.model.aggregate.Customer;
import com.google.common.eventbus.EventBus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

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

    @Bean
    public EventBus eventBus(){
        return new EventBus();
    }

}



@RepositoryRestResource
public interface CustomerRepository extends JpaRepository<Customer, Long>{
    @RestResource(path = "by-fullname")
    Customer getByFirstnameAndLastname(@Param("firstname") String firstname, @Param("lastname") String lastname);
}

