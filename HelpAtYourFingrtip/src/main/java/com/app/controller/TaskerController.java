package com.app.controller;

import java.io.IOException;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	public ResponseEntity<?> addNewTasker( @RequestParam MultipartFile imageFile,@PathVariable int taskerid) throws IOException { // @RequestBody
																												// -->
																												// Json
																												// ->
																												// Java
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(taskerService.insertImage(taskerid,imageFile));

	}
}
