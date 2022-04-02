package com.app.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.custom_exceptions.WrongInputException;
import com.app.dao.ICustomerRepository;
import com.app.dao.IJobRepository;
import com.app.dao.ITaskerRepository;
import com.app.dto.JobStatusDTO;
import com.app.pojos.Job;
import com.app.pojos.JobStatus;


@Service
@Transactional
public class JobServiceImpl implements IJobService {
	@Autowired // autowire=byType
	private IJobRepository jobDao;
	@Autowired
	private ICustomerRepository custDao;
	@Autowired
	private ITaskerRepository taskerDao;
	@Override
	public Job insertJobDetails(Job job,int taskerId,int custId) {
		job.setCustomer(custDao.findById(custId)
				.orElseThrow(() -> new WrongInputException("Customer Id Not Found")));
		job.setTasker(taskerDao.findById(taskerId)
				.orElseThrow(() -> new WrongInputException("Tasker Id Not Found")));
		return jobDao.save(job);
	}
	@Override
	public void updateJobStatus(JobStatusDTO status) {
		Job job = jobDao.findById(status.getJobId())
				.orElseThrow(() -> new WrongInputException("Job Id Not Found"));
		job.setJobStatus(status.getStatus());
	}
	@Override
	public void updateJobStartTime(int jobId) {
		Job job = jobDao.findById(jobId)
				.orElseThrow(() -> new WrongInputException("Job Id Not Found"));
		job.setStartTime(java.time.LocalTime.now());
	}
	@Override
	public List<Job> getJobByTaskerAndStatus(int taskerId) {
		return jobDao.findJobsBytaskerIdAndStatus(taskerId);
	}
	
	@Override
	public Job updateJobPaymentStatus(int jobId) {
		Job job = jobDao.findById(jobId).orElseThrow(() -> new WrongInputException("Job Id not Found"));
		job.setPaymentStatus(true);
		return job;
	}

	@Override
	public Job updateJobRating(int jobId, int rating) {
		Job job = jobDao.findById(jobId).orElseThrow(() -> new WrongInputException("Job Id not Found"));
		job.setRating(rating);
		return job;
		
	}
	@Override
	public List<Job> getJobByCustomerAndStatus(int custId) {
		return jobDao.getJobDetailsByCustomerAndStatus(custId,JobStatus.COMPLETED,false);
	}
}
