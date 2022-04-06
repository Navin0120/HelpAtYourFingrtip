package com.app.service;

import java.util.List;

import com.app.dto.JobCost;
import com.app.pojos.Job;

public interface IJobService {
	Job insertJobDetails(Job job,int taskerId,int custId);
	List<Job> getJobByTaskerAndStatus(int taskerId);
	Job updateJobPaymentStatus(int jobId);
	Job updateJobRating(int jobId,int rating);
	List<Job> getJobByCustomerAndStatus(int custId);
	List<Job> getPendingJobs(int taskerId);
	void updateJobStatusAccept(int jobId);
	void updateJobStatusReject(int jobId);
	void updateJobStatusAndCost(JobCost jobcost);
}
