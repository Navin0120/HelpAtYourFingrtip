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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "jobs")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
}

