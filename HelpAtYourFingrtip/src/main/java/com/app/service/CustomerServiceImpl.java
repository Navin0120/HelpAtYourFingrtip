package com.app.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.custom_exceptions.WongInputException;
import com.app.dao.ICustomerRepository;
import com.app.pojos.Customer;

@Service // MANDATORY to tell SC : following is spring bean , containing B.L
@Transactional // annotation meant for SC , to automatically manage txs
public class CustomerServiceImpl implements ICustomerService {

	@Autowired
	ICustomerRepository custRepo;
	
	@Override
	public Customer insertCustomerDetails(Customer customer) {
		if(customer.getPassword().equals(customer.getConfirmPassword())) {
			return custRepo.save(customer);
		}
		else {
			throw new WongInputException("Confirm password should be same as password");
		}
	}

	@Override
	public Customer getCustomerDetails(int custId) {
		return custRepo.findById(custId).orElseThrow(()-> new WongInputException("Customer Id Not Found")) ;
	}
}
