package com.app.pojos;

import java.util.ArrayList;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "taskers")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Tasker extends BaseEntity {
	//Spring --> firstName --> first_name(db)
	@NotBlank(message = "Firstname can't be blank")
	@Column(length=15)
	private String firstName;
	
	@NotBlank(message = "LastName can't be blank")
	@Column(length=15)
	private String lastName;
	
	@NotBlank(message = "Email can't be blank")
	@Column(length=25, unique=true)
	@Email
	private String email;

	@NotBlank(message = "Password can't be blank")
	private String password;
	
	@Transient
	private String confirmPassword;

	@NotBlank(message = "AadharCard No can't be blank")
	@Column(length = 15,unique=true)
	@Pattern(regexp = "(\\d{12})", message = "Invalid Aadhar Card No")
	private String aadharNo;

	@NotBlank(message="City should not be blank")
	@Column(length=20)
	private String city;

	@NotBlank(message="Address should not be blank")
	private String address;
	
	@NotBlank(message = "Contact No can't be blank")
	@Column(length=12)
	@Pattern(regexp = "(\\d{10})", message = "invalid contact no")
	private String contactNo;

	private double overallRating;
	private String bio;

	@ElementCollection(fetch=FetchType.EAGER) //Skills are very few , so we can fetch it eagerly
	@CollectionTable(name = "skills", joinColumns = @JoinColumn(name = "tasker_id"))
	List<Skill> services = new ArrayList<>();
	
	public boolean isServicePresent(String skill) {
		for(Skill sk : services) {
			if(sk.getSkillName().equals(AvailableServices.valueOf(skill))) {
				return true;
			}
		}
		return false;
	}
}
