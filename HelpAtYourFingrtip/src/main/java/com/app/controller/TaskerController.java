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

import com.app.pojos.Tasker;
import com.app.service.ITaskerService;


@RestController
@RequestMapping("/tasker")
@CrossOrigin(origins="http://localhost:3000/")
public class TaskerController {
	@Autowired
	private ITaskerService taskerService;
	TaskerController(){
		System.out.println("in constructor "+ getClass());
	}
	
	@GetMapping("location/{location}/skill/{skill}")
	public ResponseEntity<?> getTaskerDetailsByLocationAndSkill(@PathVariable String location,
			@PathVariable String skill) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(taskerService.getTaskerDetailsByLocationAndSkill(location, skill));
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> addNewTasker(@RequestBody Tasker tasker){ //@RequestBody --> Json -> Java
		return ResponseEntity.status(HttpStatus.CREATED).body(taskerService.insertTaskerDetails(tasker));
	}
	@PostMapping("/login")
	public ResponseEntity<?> processTaskerLoginForm(@RequestBody Tasker tasker,
													HttpSession session){
		Tasker task =  taskerService.authenticateTasker(tasker.getEmail(), tasker.getPassword()); 
		session.setAttribute("user_details", task);// store it under session scope (till logout)
		return ResponseEntity.ok().body("Login Successful");
	}
	@GetMapping("/{taskerId}")
	public ResponseEntity<?> getTaskerDetails(@PathVariable int taskerId) {
			return ResponseEntity.ok(taskerService.getTaskerDetails(taskerId));
	}
}
