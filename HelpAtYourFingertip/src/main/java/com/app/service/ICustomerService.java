package com.app.service;

import com.app.pojos.Customer;

public interface ICustomerService {
	Customer getCustomerFromUserId(int id);
	Customer getCustomerDetails(int custId); 
	 Customer updateCustomerDetails(Customer customer,int id); 
	 int getCustomerIdFromUserId(int id);
}
