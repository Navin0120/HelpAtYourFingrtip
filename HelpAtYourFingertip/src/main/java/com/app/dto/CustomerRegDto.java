package com.app.dto;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CustomerRegDto {
	
	private String email;
    private String password;

	private String firstName;
	
	private String lastName;
	
	private String address;
	private String aadharNo;
	
	private String contactNo;
	private Set<String> roles =new HashSet<>(Arrays.asList("ROLE_CUSTOMER"));
	

	public CustomerRegDto(String email, String password, String firstName, String lastName, String address,
			String aadharNo, String contactNo, Set<String> roles) {
		super();
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.aadharNo = aadharNo;
		this.contactNo = contactNo;
		this.roles = roles;
	}

	public CustomerRegDto() {
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

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
	


}
