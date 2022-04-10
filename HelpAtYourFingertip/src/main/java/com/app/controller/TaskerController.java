package com.app.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.pojos.Tasker;
import com.app.pojos.User;
import com.app.service.ITaskerService;
import com.app.service.IUserService;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/tasker")
public class TaskerController {
	@Autowired
	private IUserService userService;
	@Autowired
	private ITaskerService taskerService;

	public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/images";

	TaskerController() {
		System.out.println("in constructor " + getClass());
	}
	
	//customer
	@PreAuthorize("hasRole('CUSTOMER')")
	@GetMapping("location/{location}/skill/{skill}/{page}")
	public ResponseEntity<?> getTaskerDetailsByLocationAndSkill(@PathVariable String location,
			@PathVariable String skill,@PathVariable int page) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(taskerService.getTaskerDetailsByLocationAndSkill(location, skill, page));
	}
	
	//tasker
	@PreAuthorize("hasRole('TASKER')")
	@PostMapping(value = "/image/{taskerid}")
	public ResponseEntity<?> addNewTasker(@RequestParam MultipartFile imageFile, @PathVariable int taskerid) {
		try {
			User user=userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
			int taskerId=taskerService.getTaskerIdFromUserId(user.getId());
			return ResponseEntity.status(HttpStatus.CREATED).body(taskerService.insertImage(taskerId, imageFile));
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}
	
	//tasker
	
	@GetMapping("/image/{taskerId}")
	public ResponseEntity<byte[]> getFile(@PathVariable int taskerId) throws IOException {
		Tasker tasker = taskerService.getTaskerDetails(taskerId);
		Path path = Paths.get(uploadDirectory, tasker.getImage());
		System.out.println(path);
		byte[] imageData = Files.readAllBytes(path);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + tasker.getImage() + "\"")
				.body(imageData);
	}
	
}
