package com.app;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.app.controller.CustomerController;
import com.app.pojos.Customer;
import com.app.service.ICustomerService;
import com.app.service.IJobService;
import com.fasterxml.jackson.databind.ObjectMapper;

//UNIT TEST -> CONTROLLER TEST -> mocking service layer(Using Mockito) and doesn't even start the server
@WebMvcTest(controllers = CustomerController.class) // Mainly for testing only Spring MVC components
//(Start Lifecycle of only CustomerController)
//In this case , configures only CustomerController class and no other beans
public class TestCustomerController {
	@Autowired 
	private CustomerController controller;
	
	@Autowired
	private MockMvc mockMvc;// entry point to testing MVC : simulates HTTP requests.
	
	@MockBean // replaces CustomerService by it's mock (method are not delegated to actual
	// implementation class) Controller---> calls Service Layer method --> control delegated to its mock
	private ICustomerService cusService;
	
	@MockBean 
	private IJobService jobService;
	
	@Autowired
	private ObjectMapper mapper;// Jackson supplied class for JSON processing.(marshalling & unmarshalling)
	
	@Test
	void sanityTest() {
		assertNotNull(controller);// To confirm if CustomerController is autowired correctly.
	}
/*
	@Test
	public void testGetCustomerDetailsByIdPathVar() throws Exception{	//Run As --> Junit Test
		Customer c = new Customer("Rahul","Singh","rahul@gmail.com","123","123","Pune","123412341234","1234567890");
		when(cusService.getCustomerDetails(12)).thenReturn(c); //when(mock.someMethod()).thenReturn(10);
		mockMvc.perform(get("/customer/detail/12")). //simulating GET http request
		andExpect(jsonPath("$.email").value("rahul@gmail.com")). //in resulting JSON : checks key name and asserts its value 
		andExpect(status().isOk()); //chks if HttpStatus is OK
	}
	
	@Test
	public void testAddNewCustomer() throws Exception {
		Customer c = new Customer("Rahul","Singh","rahul@gmail.com","123","123","Pune","123412341234","1234567890");
		c.setId(21);
		when(cusService.insertCustomerDetails(any(Customer.class))).thenReturn(c); 
		mockMvc.perform(post("/customer/register"). //performs a post request
				content(jsonString(c)).	//setting request body as c
				contentType(MediaType.APPLICATION_JSON)).  //setting request's content type header
				andExpect(status().isCreated()). //checks if HttpStatus is OK
				andExpect(jsonPath("$.email").value("rahul@gmail.com")).
				andExpect(jsonPath("$.id").value(21));
	}
*/
	public String jsonString(Object obj) throws Exception {
		return mapper.writeValueAsString(obj); //marshalling
	}
}
