package com.app.pojos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "customers")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Customer extends BaseEntity {
	
	@NotBlank(message = "firstname can't be blank")
	@Column(length = 15)
	private String firstName;
	
	@NotBlank(message = "lastName can't be blank")
	@Column(length = 15)
	private String lastName;

	@NotBlank(message = "email can't be blank")
	@Column(unique = true,length=25)
	@Email
	private String email;
	
	@NotBlank(message = "password can't be blank")
	private String password;
	
	@Transient
	private String confirmPassword;
	
	private String address;
	
	@NotBlank(message = "AadharCard No can't be blank")
	@Column(length=15,unique=true)
	@Pattern(regexp = "(\\d{12})", message = "invalid aadhar card no")
	private String aadharNo;
	
	@NotBlank(message = "contactNo can't be blank")
	@Column(length = 12)
	@Pattern(regexp = "(\\d{10})", message = "invalid contact no")
	private String contactNo;
}
