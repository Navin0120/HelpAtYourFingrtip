package com.app.service;

import com.app.pojos.Customer;

public interface ICustomerService {
	Customer getCustomerFromUserId(int id);
	Customer getCustomerDetails(int custId); 
	 int getCustomerIdFromUserId(int id);
}
