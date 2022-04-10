package com.app.service;

import com.app.dto.CustomerRegDto;
import com.app.dto.TaskerRegDto;
import com.app.pojos.Customer;
import com.app.pojos.Tasker;
import com.app.pojos.User;

//Nothing to do with spring security : it's job currently is user registration
public interface IUserService {
	Customer registerCustomer(CustomerRegDto request);
	Tasker registerTasker(TaskerRegDto request);
	void changePassword(String email,String password);
	User findUserByEmail(String email);
	boolean checkIfValidOldPassword(User user,String oldPassword);
	void changeUserPassword(User user,String newPassword);
}
