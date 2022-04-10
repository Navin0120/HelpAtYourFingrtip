package com.app.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.service.IJobService;

@RestController // @Controller + @ResponseBody --> Indicates a method return value should be bound to the web response body
								//Java -> Json
@CrossOrigin(origins="http://localhost:3000/")
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private IJobService jobService;
	
	CustomerController(){
		System.out.println("in constructor "+ getClass());
	}
	
	@GetMapping(value = "/payment/{id}/{page}")
	public ResponseEntity<?> getJobDetails(@PathVariable int id,@PathVariable int page) {
		return ResponseEntity.status(HttpStatus.OK).body(jobService.getJobByCustomerAndStatus(id,page));
	}
}
