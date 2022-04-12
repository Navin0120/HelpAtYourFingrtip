package com.app.pojos;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "jobs")


public class Job extends BaseEntity{

	//Parent : Non Owning Side : Tasker , Customer
	//Child : Owning Side(Fk : tasker_id,customer_id) : Job
	//Cascade(parent is modified/added --> modify/add corresponding child)
	@ManyToOne(cascade = CascadeType.ALL)	
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "tasker_id", nullable = false)
	private Tasker tasker;
	
	@Enumerated(EnumType.STRING)
	private AvailableServices skillName;

	@Range(min = 0,max=5)
	private int rating;

	private double cost;

	@Enumerated(EnumType.STRING)
	private JobStatus jobStatus=JobStatus.BOOKED;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate jobDate; 

	@Column(length = 20)
	@NotBlank(message = "City can't be blank")
	private String city; 

	@NotBlank(message = "Job Address can't be blank")
	private String jobAddress;
	
	@NotBlank(message = "Job Details can't be blank")
	private String jobDetails;
	
	private boolean paymentStatus;

	public Job() {
		super();
	}

	public Job(Customer customer, Tasker tasker, AvailableServices skillName, @Range(min = 0, max = 5) int rating,
			double cost, JobStatus jobStatus, LocalDate jobDate, @NotBlank(message = "City can't be blank") String city,
			@NotBlank(message = "Job Address can't be blank") String jobAddress,
			@NotBlank(message = "Job Details can't be blank") String jobDetails, boolean paymentStatus) {
		super();
		this.customer = customer;
		this.tasker = tasker;
		this.skillName = skillName;
		this.rating = rating;
		this.cost = cost;
		this.jobStatus = jobStatus;
		this.jobDate = jobDate;
		this.city = city;
		this.jobAddress = jobAddress;
		this.jobDetails = jobDetails;
		this.paymentStatus = paymentStatus;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Tasker getTasker() {
		return tasker;
	}

	public void setTasker(Tasker tasker) {
		this.tasker = tasker;
	}

	public AvailableServices getSkillName() {
		return skillName;
	}

	public void setSkillName(AvailableServices skillName) {
		this.skillName = skillName;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public JobStatus getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(JobStatus jobStatus) {
		this.jobStatus = jobStatus;
	}

	public LocalDate getJobDate() {
		return jobDate;
	}

	public void setJobDate(LocalDate jobDate) {
		this.jobDate = jobDate;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getJobAddress() {
		return jobAddress;
	}

	public void setJobAddress(String jobAddress) {
		this.jobAddress = jobAddress;
	}

	public String getJobDetails() {
		return jobDetails;
	}

	public void setJobDetails(String jobDetails) {
		this.jobDetails = jobDetails;
	}

	public boolean isPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(boolean paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	
}

