package com.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.app.pojos.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;

//INTEGRATION TEST 

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) //Start Full fledged application context-->Create All Bean --> Controller,Service,Dao
															// tests all layers from Controller to DB (only simulating Http requests)
@AutoConfigureMockMvc
public class MockMvcWithServer {
	@LocalServerPort
	private int serverPort; // randomly available free port is injected in local server port

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;
/*
	@Test
	public void testGetCustomerDetailsByIdPathVar() throws Exception{	//Run As --> Junit Test
		mockMvc.perform(get("/customer/detail/6")). //simulating GET http request
		andExpect(status().isOk()).  //checks if HttpStatus is OK
		andDo(print()) //print complete http mock request detail
		.andExpect(jsonPath("$.firstName").value("Rahul"));
	}
	
	@Test
	public void testAddNewCustomer() throws Exception {
		Customer c = new Customer("Rahul","Singh","rahul@gmail.com","123","123","Pune","123412341234","1234567890");
		MvcResult result = mockMvc.perform(post("/customer/register"). //performs a post request
				contentType(MediaType.APPLICATION_JSON).  //setting request's content type header
				content(mapper.writeValueAsString(c))).	//body
				andDo(print()).andExpect(status().isCreated()).andReturn();
		c.setId(6);
		assertEquals(mapper.writeValueAsString(c), result.getResponse().getContentAsString());//Matching Expected with actual
	}
*/
}


//In case Microservices --> we use TestRestTemplate(Using Dependency Injection) --> To send actual http requests --> only RestTemplate is mocked
//Alternative --> POSTMAN