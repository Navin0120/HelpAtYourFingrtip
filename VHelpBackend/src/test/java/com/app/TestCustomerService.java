 package com.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.app.pojos.Customer;
import com.app.service.ICustomerService;

//SERVICE LAYER TEST

@SpringBootTest
public class TestCustomerService {
	@Autowired
	private ICustomerService service;
	@Test
	public void testGetCustomerDetails() {
		Customer c = service.getCustomerDetails(1);
		assertEquals("Rahul", c.getFirstName());
	}
}
