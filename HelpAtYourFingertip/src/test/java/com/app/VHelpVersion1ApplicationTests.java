package com.app;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.app.controller.TestController;

//SMOKE TEST -> Check if our spring boot container up and running

@SpringBootTest //All the beans are configured -> Spring Boot Container is running in debug mode
class VHelpVersion1ApplicationTests {
	
	@Autowired
	private TestController controller;
	
	@Test
	void contextLoads() {
		System.out.println("in test");
		assertNotNull(controller); //value to test controller should not be null
	}

}
