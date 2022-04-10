package com.app.pojos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "taskers")

@ToString
@Getter
@Setter
public class Tasker extends BaseEntity {
	// Spring --> firstName --> first_name(db)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id",nullable = false)
	@JsonIgnore
	private User tasker;
	
	@NotBlank(message = "Firstname can't be blank")
	@Column(length = 15)
	private String firstName;

	@NotBlank(message = "LastName can't be blank")
	@Column(length = 15)
	private String lastName;

	@NotBlank(message = "AadharCard No can't be blank")
	@Column(length = 15, unique = true)
	@Pattern(regexp = "(\\d{12})", message = "Invalid Aadhar Card No")
	private String aadharNo;

	@NotBlank(message = "City should not be blank")
	@Column(length = 20)
	private String city;

	@NotBlank(message = "Contact No can't be blank")
	@Column(length = 12)
	@Pattern(regexp = "(\\d{10})", message = "invalid contact no")
	private String contactNo;

	private double overallRating;
	private String bio;
	private String image;

	@ElementCollection(fetch = FetchType.EAGER) // Skills are very few , so we can fetch it eagerly
	@CollectionTable(name = "skills", joinColumns = @JoinColumn(name = "tasker_id"))
	List<Skill> services = new ArrayList<>();

	public Tasker() {
		super();
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

	public boolean isServicePresent(String skill) {
		for (Skill sk : services) {
			if (sk.getSkillName().equals(AvailableServices.valueOf(skill))) {
				return true;
			}
		}
		return false;
	}


	public Tasker(User tasker, @NotBlank(message = "Firstname can't be blank") String firstName,
			@NotBlank(message = "LastName can't be blank") String lastName,
			@NotBlank(message = "AadharCard No can't be blank") @Pattern(regexp = "(\\d{12})", message = "Invalid Aadhar Card No") String aadharNo,
			@NotBlank(message = "City should not be blank") String city,
			@NotBlank(message = "Contact No can't be blank") @Pattern(regexp = "(\\d{10})", message = "invalid contact no") String contactNo,
			double overallRating, String bio, String image, List<Skill> services) {
		super();
		this.tasker = tasker;
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



}
