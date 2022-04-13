package com.app.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.JobRating;
import com.app.pojos.Job;
import com.app.pojos.Tasker;
import com.app.pojos.User;
import com.app.service.ICustomerService;
import com.app.service.IJobService;
import com.app.service.ITaskerService;
import com.app.service.IUserService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@RestController // @Controller + @ResponseBody --> Indicates a method return value should be
				// bound to the web response body
				// Java -> Json
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	private IJobService jobService;
	@Autowired
	private ITaskerService taskerService;
	@Autowired
	private ICustomerService cusService;
	@Autowired
	private IUserService userService;
	public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/images";

	CustomerController() {
		System.out.println("in constructor " + getClass());
	}

	

	//customer..
	@PreAuthorize("hasRole('CUSTOMER')")
	@PostMapping("/addJob/{taskerId}")
	public ResponseEntity<?> addNewJob(@PathVariable int taskerId,@RequestBody Job job){ //@RequestBody --> Json -> Java
		User user=userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		int customerid=cusService.getCustomerIdFromUserId(user.getId());
		return ResponseEntity.status(HttpStatus.CREATED).body(jobService.insertJobDetails(job,taskerId,customerid));
	}

	//customer
	@PreAuthorize("hasRole('CUSTOMER')")
	@PatchMapping("/paymentStatus/{jobId}")
	public ResponseEntity<?> updatePaymentStatusCompleted(@PathVariable int jobId ){
		jobService.updateJobPaymentStatus(jobId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	//customer
	@PreAuthorize("hasRole('CUSTOMER')")
	@GetMapping("/completedJob/{page}")
	public ResponseEntity<?> getJobDetailsByCustomerAndStatus(@PathVariable int page) {
		User user=userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		int customerid=cusService.getCustomerIdFromUserId(user.getId());
			return ResponseEntity.ok(jobService.getJobByCustomerAndStatus(customerid,page));
	}
	//customer
	@PreAuthorize("hasRole('CUSTOMER')")
	@GetMapping("/customerHistory/{customerId}/{page}")
	public ResponseEntity<?> getJobHistoryOfCustomerr(@PathVariable int page) {
		User user=userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		int customerid=cusService.getCustomerIdFromUserId(user.getId());
			return ResponseEntity.ok(jobService.getTaskHistoryByCustomerId(customerid,page));
		
	}
	//customer
	@PreAuthorize("hasRole('CUSTOMER')")
	@GetMapping("/bookedJob/{customerId}/{page}")
	public ResponseEntity<?> getBookedJobByCustomerAndStatus(@PathVariable int customerId,@PathVariable int page) {
			return ResponseEntity.ok(jobService.getBookedTaskByCustomerId(customerId,page));
	}
	//customer
	@PostMapping("/create_order")
	public ResponseEntity<?> createOrder(@RequestBody JobRating jobrating) throws RazorpayException
	{
		System.out.println("createOrder");
		Job job = jobService.updateJobRating(jobrating.getJobId(),jobrating.getRating());
		taskerService.updateOverallRating(job.getTasker().getId());
		RazorpayClient client = new RazorpayClient("rzp_test_oQkaRRmRbbzbrZ","R1CZO1zDD4NKTcKvVbv0UoDr");
		JSONObject obj = new JSONObject();
		obj.put("amount", job.getCost()*100); //Cost in paise
		obj.put("currency", "INR");
		obj.put("receipt", "tx_"+job.getId());
		Order order = client.Orders.create(obj);
		if(order.get("status").equals("created"))
		System.out.println(order);
		return ResponseEntity.ok(order.toString());
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
	@PreAuthorize("hasRole('CUSTOMER')")
	@GetMapping("/tasker/image/{taskerId}")
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
