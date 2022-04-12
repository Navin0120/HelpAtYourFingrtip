package com.app.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.app.pojos.Skill;

public class TaskerRegDto {

	private String email;
	private String password;
	
	private String firstName;


	private String lastName;

	private String aadharNo;

	private String city;

	private String contactNo;
	private Set<String> roles =new HashSet<>(Arrays.asList("ROLE_TASKER"));
	private double overallRating;
	private String bio;
	private String image;
	List<Skill> services = new ArrayList<>();
	public TaskerRegDto() {
		super();
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getAadharNo() {
		return aadharNo;
	}
	public void setAadharNo(String aadharNo) {
		this.aadharNo = aadharNo;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public List<Skill> getServices() {
		return services;
	}
	public void setServices(List<Skill> services) {
		this.services = services;
	}
	public TaskerRegDto(String email, String password, String firstName, String lastName, String aadharNo, String city,
			String contactNo, double overallRating, String bio, String image, List<Skill> services) {
		super();
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.aadharNo = aadharNo;
		this.city = city;
		this.contactNo = contactNo;
		this.overallRating = overallRating;
		this.bio = bio;
		this.image = image;
		this.services = services;
	}
	public Set<String> getRoles() {
		return roles;
	}
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
	public TaskerRegDto(String email, String password, String firstName, String lastName, String aadharNo, String city,
			String contactNo, Set<String> roles, double overallRating, String bio, String image, List<Skill> services) {
		super();
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.aadharNo = aadharNo;
		this.city = city;
		this.contactNo = contactNo;
		this.roles = roles;
		this.overallRating = overallRating;
		this.bio = bio;
		this.image = image;
		this.services = services;
	}


}
