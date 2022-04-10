package com.app.dto;

public class ChangePassword {
	String password;
	String newPassword;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public ChangePassword(String email, String password, String newPassword) {
		super();
		
		this.password = password;
		this.newPassword = newPassword;
	}

	
	public ChangePassword() {
		super();
	}
}
