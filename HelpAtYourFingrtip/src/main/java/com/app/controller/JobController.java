package com.app.controller;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.app.service.IJobService;
import com.app.service.ITaskerService;
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
	
	JobController(){
		System.out.println("in constructor "+ getClass());
	}
	
	@PostMapping("/add/{taskerId}/{custId}")
	public ResponseEntity<?> addNewJob(@PathVariable int taskerId,@PathVariable int custId,@RequestBody Job job){ //@RequestBody --> Json -> Java
		return ResponseEntity.status(HttpStatus.CREATED).body(jobService.insertJobDetails(job,taskerId,custId));
	}
	
	@PatchMapping("/accept/{jobId}")
	public ResponseEntity<?> updateJobStatusAccept(@PathVariable int jobId){
			jobService.updateJobStatusAccept(jobId);
			return ResponseEntity.ok().build();
	}
	
	@PatchMapping("/reject/{jobId}")
	public ResponseEntity<?> updateJobStatusReject(@PathVariable int jobId){
			jobService.updateJobStatusReject(jobId);
			return ResponseEntity.ok().build();
	}
	
	@GetMapping("/home/{taskerId}")
	public ResponseEntity<?> getJobDetailsByTaskerAndStatus(@PathVariable int taskerId) {
			return ResponseEntity.ok(jobService.getJobByTaskerAndStatus(taskerId));
	}
	

	@PatchMapping("/paymentStatus/{jobId}")
	public ResponseEntity<?> updatePaymentStatusCompleted(@PathVariable int jobId ){
		jobService.updateJobPaymentStatus(jobId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping("/pendingJobs/{taskerId}")
	public ResponseEntity<?> getJobDetailsofpendingTasks(@PathVariable int taskerId) {
			return ResponseEntity.ok(jobService.getPendingJobs(taskerId));
	}
	
	@PatchMapping("/updateJobStatusAndCost")
	public ResponseEntity<?> updateJobStatusAndCost(@RequestBody JobCost jobcost) {
		jobService.updateJobStatusAndCost(jobcost);
		return ResponseEntity.status(HttpStatus.OK).build();
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
	@GetMapping("/completedJob/{customerId}/{page}")
	public ResponseEntity<?> getJobDetailsByCustomerAndStatus(@PathVariable int customerId,@PathVariable int page) {
		System.out.println(customerId+" "+page);
		System.out.println(jobService.getJobByCustomerAndStatus(customerId,page));
			return ResponseEntity.ok(jobService.getJobByCustomerAndStatus(customerId,page));
	}
}
