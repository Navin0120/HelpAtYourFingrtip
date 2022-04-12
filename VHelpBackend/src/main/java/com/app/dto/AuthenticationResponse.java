package com.app.dto;

public class AuthenticationResponse {
	private final String jwt;
	private int id;
	private String firstName;
	private String lastName ;
	private String role;
	public AuthenticationResponse(String jwt, int id, String firstName, String lastName,String role) {
		super();
		this.jwt = jwt;
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getJwt() {
		return jwt;
	}

	
	
}
