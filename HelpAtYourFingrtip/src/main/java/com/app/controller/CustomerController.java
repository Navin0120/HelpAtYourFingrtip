package com.app.controller;

import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.pojos.Customer;
import com.app.service.ICustomerService;
import com.app.service.IJobService;

@RestController // @Controller + @ResponseBody --> Indicates a method return value should be bound to the web response body
								//Java -> Json
@CrossOrigin(origins="http://localhost:3000/")
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	private ICustomerService cusService;
	
	@Autowired
	private IJobService jobService;
	
	CustomerController(){
		System.out.println("in constructor "+ getClass());
	}
	@PostMapping("/register")
	public ResponseEntity<?> addNewCustomer(@RequestBody Customer customer){ //@RequestBody --> Json -> Java
		return ResponseEntity.status(HttpStatus.CREATED).body(cusService.insertCustomerDetails(customer));
	}
	@GetMapping (value="/detail/{id}")
	public ResponseEntity<?> getCustomerDetails(@PathVariable int id){
		return ResponseEntity.status(HttpStatus.OK).body(cusService.getCustomerDetails(id));
	}
	
	@GetMapping (value="/payment/{id}")
	public ResponseEntity<?> getJobDetails(@PathVariable int id){
		return ResponseEntity.status(HttpStatus.OK).body(jobService.getJobByCustomerAndStatus(id));
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> processCustomerLoginForm(@RequestBody Customer customer,
													HttpSession session){
		Customer cust =  cusService.authenticateCustomer(customer.getEmail(), customer.getPassword()); 
		session.setAttribute("customer_details", cust);// store it under session scope (till logout)
		return ResponseEntity.ok().body(cust);
	}
}
