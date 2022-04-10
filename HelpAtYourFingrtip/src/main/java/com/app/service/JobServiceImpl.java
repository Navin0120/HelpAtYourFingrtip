package com.app.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.app.custom_exceptions.WrongInputException;
import com.app.dao.ICustomerRepository;
import com.app.dao.IJobRepository;
import com.app.dao.ITaskerRepository;
import com.app.dto.JobCost;
import com.app.pojos.Customer;
import com.app.pojos.Job;
import com.app.pojos.JobStatus;
import com.app.pojos.Tasker;


@Service
@Transactional
public class JobServiceImpl implements IJobService {
	@Autowired // autowire=byType
	private IJobRepository jobDao;
	@Autowired
	private ICustomerRepository custDao;
	@Autowired
	private ITaskerRepository taskerDao;
	@Autowired
	private JavaMailSender sender;
	@Override
	public Job insertJobDetails(Job job,int taskerId,int custId) {
		job.setCustomer(custDao.findById(custId)
				.orElseThrow(() -> new WrongInputException("Customer Id Not Found")));
		job.setTasker(taskerDao.findById(taskerId)
				.orElseThrow(() -> new WrongInputException("Tasker Id Not Found")));
		return jobDao.save(job);
	}
	
	@Override
	public void updateJobStatusAccept(int jobId) {
		Job job = jobDao.findById(jobId)
				.orElseThrow(() -> new WrongInputException("Job Id Not Found"));
		job.setJobStatus(JobStatus.PENDING);
			sendMail(jobId,"Task Accepted");
	}
	
	@Override
	public void updateJobStatusReject(int jobId) {
		Job job = jobDao.findById(jobId)
				.orElseThrow(() -> new WrongInputException("Job Id Not Found"));
		job.setJobStatus(JobStatus.REJECTED);
			sendMail(jobId,"Task Rejected");
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
	public List<Job> getJobByCustomerAndStatus(int custId,int page) {
		return jobDao.getJobDetailsByCustomerAndStatus(custId,JobStatus.COMPLETED,false,PageRequest.of(page, 5, Sort.by(Direction.DESC,"jobDate")));
	}
	
	public void sendMail(int jobId,String subject)
	{
		SimpleMailMessage mesg=new SimpleMailMessage();
		Job job = jobDao.findById(jobId)
				.orElseThrow(() -> new WrongInputException("Job Id Not Found"));
		Tasker tasker = taskerDao.findById(job.getTasker().getId())
				.orElseThrow(() -> new WrongInputException("Tasker Id not Found"));
		Customer customer = custDao.findById(job.getCustomer().getId())
				.orElseThrow(() -> new WrongInputException("Customer Id not Found"));
		mesg.setText(subject+"\n"
				+"Task Date :"+job.getJobDate()+"\n"
				+"Task Details :"+job.getJobDetails()+"\n"
				+"Tasker Name:"+tasker.getFirstName()+" "+tasker.getLastName()+"\n"
				+"Tasker Contact No:"+tasker.getContactNo());
		System.out.println(customer.getEmail());
		mesg.setSubject(subject);
		mesg.setTo(customer.getEmail());
		sender.send(mesg);
	}
	@Override
	public List<Job> getPendingJobs(int taskerId) {
		return jobDao.findPendingTasksByTaskerId(taskerId);
	}
	
	@Override
	public void updateJobStatusAndCost(JobCost jobcost) {
		Job job = jobDao.findById(jobcost.getJobId())
				.orElseThrow(() -> new WrongInputException("Job Id Not Found"));
		job.setJobStatus(JobStatus.COMPLETED);
		job.setCost(jobcost.getCost());
	}
	
	
}
