package com.example.customerservice;

import com.example.customerservice.event.CustomerRegistrationDataValidated;
import com.example.customerservice.model.aggregate.Customer;
import com.example.customerservice.repository.CustomerRepository;
import com.example.customerservice.service.CustomerIndex;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import reactor.Environment;
import reactor.bus.EventBus;
import reactor.bus.selector.Selectors;

import java.util.stream.Stream;

import static reactor.bus.selector.Selectors.$;

@EnableDiscoveryClient
@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Bean
    Environment env() {
        return Environment.initializeIfEmpty().assignErrorJournal();
    }

    @Bean
    EventBus createEventBus(Environment env) {
        return EventBus.create(env, Environment.THREAD_POOL);
    }

    /*
    @Bean
    public CommandLineRunner initEventBus(EventBus eventBus, CustomerIndex customerIndex){
        return args -> {
            eventBus.on($(CustomerRegistrationDataValidated.class.getSimpleName()), customerIndex);
        };
    }
    */


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




