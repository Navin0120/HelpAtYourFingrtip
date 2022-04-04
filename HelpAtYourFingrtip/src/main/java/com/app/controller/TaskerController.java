package com.app.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.pojos.Tasker;
import com.app.service.ITaskerService;


@RestController
@RequestMapping("/tasker")
@CrossOrigin(origins="http://localhost:3000/")
public class TaskerController {
	@Autowired
	private ITaskerService taskerService;
	public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/images";
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
		session.setAttribute("tasker_details", task);// store it under session scope (till logout)
		return ResponseEntity.ok().body(task);
	}
	@GetMapping("/{taskerId}")
	public ResponseEntity<?> getTaskerDetails(@PathVariable int taskerId) {
			return ResponseEntity.ok(taskerService.getTaskerDetails(taskerId));
	}
	@PutMapping("/update/{taskerId}")
	public ResponseEntity<?> processTaskerUpdate(@RequestBody Tasker tasker,@PathVariable int taskerId){
		return ResponseEntity.ok().body(taskerService.updateTaskerDetails(tasker, taskerId));
	}
	
	@PatchMapping("/updatePassword/{taskerId}")
	public ResponseEntity<?> processCustomerPassword1(@RequestBody String password,@PathVariable int taskerId){
		return ResponseEntity.ok().body(taskerService.updateTaskerPassword(password, taskerId));
	}
	
	@GetMapping("/image/{taskerId}")
	// Can be tested with browser. Will work fine with react / angular app.
	public ResponseEntity<byte[]> getFile(@PathVariable int taskerId) throws IOException {
		Tasker tasker = taskerService.getTaskerDetails(taskerId);
		Path path = Paths.get(uploadDirectory, tasker.getImage());
		byte[] imageData = Files.readAllBytes(path);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + tasker.getImage() + "\"")
				.body(imageData);
	}
	
	@PostMapping(value = "/image/{taskerid}")
	public ResponseEntity<?> addNewTasker( @RequestParam MultipartFile imageFile,@PathVariable int taskerid) { // @RequestBody
																												// -->
																												// Json
																												// ->
																												// Java
		try {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(taskerService.insertImage(taskerid,imageFile));
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}
}
