package com.app.pojos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "customers")


public class Customer extends BaseEntity {
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id",nullable = false)
	@JsonIgnore
	private User customer;
	@NotBlank(message = "firstname can't be blank")
	@Column(length = 15)
	private String firstName;
	
	@NotBlank(message = "lastName can't be blank")
	@Column(length = 15)
	private String lastName;

	
	private String address;
	
	@NotBlank(message = "AadharCard No can't be blank")
	@Column(length=15,unique=true)
	@Pattern(regexp = "(\\d{12})", message = "invalid aadhar card no")
	private String aadharNo;
	
	@NotBlank(message = "contactNo can't be blank")
	@Column(length = 12)
	@Pattern(regexp = "(\\d{10})", message = "invalid contact no")
	private String contactNo;

	public Customer() {
		super();
	}


	public Customer(User customer, @NotBlank(message = "firstname can't be blank") String firstName,
			@NotBlank(message = "lastName can't be blank") String lastName, String address,
			@NotBlank(message = "AadharCard No can't be blank") @Pattern(regexp = "(\\d{12})", message = "invalid aadhar card no") String aadharNo,
			@NotBlank(message = "contactNo can't be blank") @Pattern(regexp = "(\\d{10})", message = "invalid contact no") String contactNo) {
		super();
		this.customer = customer;
		this.firstName = firstName;
		this.lastName = lastName;
		
		this.address = address;
		this.aadharNo = aadharNo;
		this.contactNo = contactNo;
	}


	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAadharNo() {
		return aadharNo;
	}

	public void setAadharNo(String aadharNo) {
		this.aadharNo = aadharNo;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}


	public User getCustomer() {
		return customer;
	}


	public void setCustomer(User customer) {
		this.customer = customer;
	}
	
}
