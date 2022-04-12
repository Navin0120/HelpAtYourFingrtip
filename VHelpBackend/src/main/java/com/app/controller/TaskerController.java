package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.JobCost;
import com.app.pojos.User;
import com.app.service.IJobService;
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
	@Autowired
	private IJobService jobService;

	public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/images";

	TaskerController() {
		System.out.println("in constructor " + getClass());
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
	

	
	//tasker...
	@PreAuthorize("hasRole('TASKER')")
	@GetMapping("/home/{taskerId}/{page}")
	public ResponseEntity<?> getJobDetailsByTaskerAndStatus(@PathVariable int page) {
		User user=userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		int taskerid=taskerService.getTaskerIdFromUserId(user.getId());
			return ResponseEntity.ok(jobService.getJobByTaskerAndStatus(taskerid,page));
	}

	
	//tasker
	@PreAuthorize("hasRole('TASKER')")
	@PatchMapping("/accept/{jobId}")
	public ResponseEntity<?> updateJobStatusAccept(@PathVariable int jobId){
			jobService.updateJobStatusAccept(jobId);
			return ResponseEntity.ok().build();
	}
	
	//tasker
	@PreAuthorize("hasRole('TASKER')")
	@PatchMapping("/reject/{jobId}")
	public ResponseEntity<?> updateJobStatusReject(@PathVariable int jobId){
			jobService.updateJobStatusReject(jobId);
			return ResponseEntity.ok().build();
	}
	

	
	//tasker
	@PreAuthorize("hasRole('TASKER')")
	@GetMapping("/pendingJobs/{taskerId}/{page}")
	public ResponseEntity<?> getJobDetailsofpendingTasks(@PathVariable int page) {
		User user=userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		int taskerid=taskerService.getTaskerIdFromUserId(user.getId());
			return ResponseEntity.ok(jobService.getPendingJobs(taskerid,page));
	}
	
	//tasker
	@PreAuthorize("hasRole('TASKER')")
	@GetMapping("/taskerHistory/{taskerId}/{page}")
	public ResponseEntity<?> getJobHistoryOfTasker(@PathVariable int page) {
			User user=userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
			int taskerid=taskerService.getTaskerIdFromUserId(user.getId());
			return ResponseEntity.ok(jobService.getTaskHistoryByTaskerId(taskerid,page));
	}
	

	
	//tasker
	@PreAuthorize("hasRole('TASKER')")
	@PatchMapping("/updateJobStatusAndCost")
	public ResponseEntity<?> updateJobStatusAndCost(@RequestBody JobCost jobcost) {
		jobService.updateJobStatusAndCost(jobcost);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	
}
