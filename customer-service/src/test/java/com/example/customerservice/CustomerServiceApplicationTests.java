package com.example.customerservice;

import com.example.customerservice.controller.RegistrationController;
import com.example.customerservice.model.dto.CustomerRegistrationData;
import com.example.customerservice.model.valueobject.CustomerIdentifier;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.Month;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-controller-tests.properties")
public class CustomerServiceApplicationTests {

	@Autowired
	RegistrationController registrationController;

	//@Test
	public void contextLoads() {
	}

	@Test
	public void shouldSendEvents() throws InterruptedException, ExecutionException, TimeoutException {
		ResponseEntity responseEntity = registrationController.registerExistingCustomer(CustomerIdentifier.from("123456", "extern"),
				new CustomerRegistrationData(1L, "Mustermann", LocalDate.of(1980, Month.APRIL, 14), "50674"));

		Assert.assertEquals(201, responseEntity.getStatusCodeValue());
	}

}
