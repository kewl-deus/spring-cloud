package com.example.customerservice;

import com.example.customerservice.controller.RegistrationController;
import com.example.customerservice.model.aggregate.Customer;
import com.example.customerservice.model.dto.CustomerRegistrationData;
import com.example.customerservice.model.valueobject.CustomerIdentifier;
import com.example.customerservice.model.valueobject.Name;
import com.example.customerservice.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import static org.assertj.core.api.Assertions.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-controller-tests.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerServiceApplicationTests {

    @Autowired
    RegistrationController registrationController;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    WebTestClient webTestClient;

    //@Test
    public void contextLoads() {
    }

    @Test
    public void shouldSendEvents() {
        Mono<ResponseEntity> responseMono = registrationController.registerExistingCustomer(CustomerIdentifier.from("123456", "extern"),
                new CustomerRegistrationData(1L, "Mustermann", LocalDate.of(1980, Month.APRIL, 14), "50674"));

        ResponseEntity responseEntity = responseMono.block(Duration.ofSeconds(10));
        System.out.println("*******************************************");
        System.out.println(responseEntity);
        System.out.println("*******************************************");
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);

    }


    @Test
    public void shouldRegisterExistingUser() {
        Customer existingCustomer = customerRepository.save(new Customer(Name.of("Hulk"), Name.of("Hogan")));
        assertThat(existingCustomer.getId()).isPositive();

        webTestClient.post()
                .uri("/customers/registrations")
                .header("idToken", "{key:'5d438c12-343b-49f2-810d-98d2515fe7be', type:'external'}")
                .contentType(new MediaType("application", "vnd.registration.existingcustomer+json", Collections.singletonMap("version", "1")))
                .bodyValue(new CustomerRegistrationData(existingCustomer.getId(), existingCustomer.getLastname(), LocalDate.of(1966, Month.JUNE, 6), "50674"))
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists("Location");
    }

}

