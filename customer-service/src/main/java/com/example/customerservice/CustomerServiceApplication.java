package com.example.customerservice;

import com.example.customerservice.event.sourcing.EventBus;
import com.example.customerservice.model.aggregate.Customer;
import com.example.customerservice.model.valueobject.Name;
import com.example.customerservice.repository.CustomerRepository;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import reactor.tools.agent.ReactorDebugAgent;

import java.util.stream.Stream;

@EnableDiscoveryClient
@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    @Lazy(false)
    @Bean
    EventBus createEventBus() {
        return new EventBus();
    }

    @Bean
    public ApplicationRunner configureReactorDebugging(){
        return args -> {
            //Hooks.onOperatorDebug()
            ReactorDebugAgent.init();
        };
    }

    @Bean
    public ApplicationRunner dummyDataGenerator(CustomerRepository customerRepository){
        return args -> {
            String[] customers = "Max Mustermann, James Bond, Karl Müller, Ethan Hunt, Indiana Jones, Guybrush Threepwood".split(", ");
            Stream.of(customers)
                    .map(s -> s.split(" "))
                    .map(strings -> new Name[]{Name.of(strings[0]), Name.of(strings[1])})
                    .map(names -> new Customer(names[0], names[1]))
                    .forEach(c -> {
                        customerRepository.save(c);
                        System.out.println("Saved " + c.getId() + " --> " + c.getLastname());
                    });
        };
    }
}




