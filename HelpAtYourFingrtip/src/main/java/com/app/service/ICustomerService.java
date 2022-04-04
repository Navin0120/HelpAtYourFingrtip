package com.app.service;

import com.app.pojos.Customer;

public interface ICustomerService {
	Customer insertCustomerDetails(Customer customer);
	Customer getCustomerDetails(int custId);
	Customer authenticateCustomer(String email,String password);
	Customer updateCustomerDetails(Customer customer,int id);
	Customer updatePassword(String password,int id);
}
