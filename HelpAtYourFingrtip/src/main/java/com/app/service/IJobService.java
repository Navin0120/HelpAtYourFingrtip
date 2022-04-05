package com.app.service;

import java.util.List;


import com.app.dto.JobStatusDTO;
import com.app.pojos.Job;

public interface IJobService {
	Job insertJobDetails(Job job,int taskerId,int custId);
	void updateJobStatus(JobStatusDTO status);
	void updateJobStartTime(int jobId);
	List<Job> getJobByTaskerAndStatus(int taskerId);
	Job updateJobPaymentStatus(int jobId);
	Job updateJobRating(int jobId,int rating);
	List<Job> getJobByCustomerAndStatus(int custId);
	List<Job> getPendingJobs(int taskerId);
	void updateJobStatusAndCost(int jobId);
}
