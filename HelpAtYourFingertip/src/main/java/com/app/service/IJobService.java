package com.app.service;

import java.util.List;

import com.app.dto.JobCost;
import com.app.dto.JobStatusDTO;
import com.app.pojos.Job;


public interface IJobService {
	Job insertJobDetails(Job job,int taskerId,int custId);
	void updateJobStatus(JobStatusDTO status);
	List<Job> getJobByTaskerAndStatus(int taskerId,int page);
	Job updateJobPaymentStatus(int jobId);
	Job updateJobRating(int jobId,int rating);
	List<Job> getJobByCustomerAndStatus(int custId,int page);
	List<Job> getPendingJobs(int taskerId,int page);
	List<Job> getTaskHistoryByTaskerId(int taskerId,int page);
	List<Job> getTaskHistoryByCustomerId(int customerId, int page);
	void updateJobStatusAccept(int jobId);
	void updateJobStatusReject(int jobId);
	void updateJobStatusAndCost(JobCost jobcost) ;
	List<Job> getBookedTaskByCustomerId(int customerId, int page);
}
