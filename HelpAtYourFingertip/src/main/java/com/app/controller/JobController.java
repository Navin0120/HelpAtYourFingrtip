package com.app.controller;


import org.json.JSONObject;

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
import org.springframework.web.bind.annotation.RestController;
import com.app.dto.JobCost;
import com.app.dto.JobRating;
import com.app.pojos.Job;
import com.app.pojos.User;
import com.app.service.ICustomerService;
import com.app.service.IJobService;
import com.app.service.ITaskerService;
import com.app.service.IUserService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@RestController
@CrossOrigin(origins="http://localhost:3000/")
@RequestMapping("/job")
public class JobController {
	@Autowired
	private IJobService jobService;
	@Autowired
	private ITaskerService taskerService;
	@Autowired
	private ICustomerService cusService;
	@Autowired
	private IUserService userService;
	
	JobController(){
		System.out.println("in constructor "+ getClass());
	}
	//customer
	@PreAuthorize("hasRole('CUSTOMER')")
	@PostMapping("/add/{taskerId}/{custId}")
	public ResponseEntity<?> addNewJob(@PathVariable int taskerId,@RequestBody Job job){ //@RequestBody --> Json -> Java
		User user=userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		int customerid=cusService.getCustomerIdFromUserId(user.getId());
		return ResponseEntity.status(HttpStatus.CREATED).body(jobService.insertJobDetails(job,taskerId,customerid));
	}
	
	//tasker
	@PreAuthorize("hasRole('TASKER')")
	@GetMapping("/home/{taskerId}/{page}")
	public ResponseEntity<?> getJobDetailsByTaskerAndStatus(@PathVariable int page) {
		User user=userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		int taskerid=taskerService.getTaskerIdFromUserId(user.getId());
			return ResponseEntity.ok(jobService.getJobByTaskerAndStatus(taskerid,page));
	}

	//customer
	@PreAuthorize("hasRole('CUSTOMER')")
	@PatchMapping("/paymentStatus/{jobId}")
	public ResponseEntity<?> updatePaymentStatusCompleted(@PathVariable int jobId ){
		jobService.updateJobPaymentStatus(jobId);
		return ResponseEntity.status(HttpStatus.OK).build();
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
	
	//customer
	@PreAuthorize("hasRole('CUSTOMER')")
	@GetMapping("/completedJob/{customerId}/{page}")
	public ResponseEntity<?> getJobDetailsByCustomerAndStatus(@PathVariable int customerId,@PathVariable int page) {
		User user=userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		int customerid=cusService.getCustomerIdFromUserId(user.getId());
			return ResponseEntity.ok(jobService.getJobByCustomerAndStatus(customerid,page));
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
	
	//customer
	@PreAuthorize("hasRole('CUSTOMER')")
	@GetMapping("/customerHistory/{customerId}/{page}")
	public ResponseEntity<?> getJobHistoryOfCustomerr(@PathVariable int page) {
		User user=userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		int customerid=cusService.getCustomerIdFromUserId(user.getId());
			return ResponseEntity.ok(jobService.getTaskHistoryByCustomerId(customerid,page));
		
	}
	
	//tasker
	@PreAuthorize("hasRole('TASKER')")
	@PatchMapping("/updateJobStatusAndCost")
	public ResponseEntity<?> updateJobStatusAndCost(@RequestBody JobCost jobcost) {
		jobService.updateJobStatusAndCost(jobcost);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	@PreAuthorize("hasRole('CUSTOMER')")
	@GetMapping("/bookedJob/{customerId}/{page}")
	public ResponseEntity<?> getBookedJobByCustomerAndStatus(@PathVariable int customerId,@PathVariable int page) {
			return ResponseEntity.ok(jobService.getBookedTaskByCustomerId(customerId,page));
	}
	
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
		obj.put("receipt", "tx_123");
		Order order = client.Orders.create(obj);
		if(order.get("status").equals("created"))
		System.out.println(order);
		return ResponseEntity.ok(order.toString());
	}
	
}
