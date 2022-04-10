package com.app.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TaskerDetails {
	private int id;
	private String firstName;
	private String lastName;
	private String contactNo;
	private double overallRating;
	private String bio;
	
	
	public TaskerDetails() {
		super();
	}
	public TaskerDetails(int id, String firstName, String lastName, String contactNo, double overallRating, String bio) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.contactNo = contactNo;
		this.overallRating = overallRating;
		this.bio = bio;
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public double getOverallRating() {
		return overallRating;
	}
	public void setOverallRating(double overallRating) {
		this.overallRating = overallRating;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	
	
	
	

}
