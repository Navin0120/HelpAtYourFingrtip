package com.app.service;

import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.custom_exceptions.WrongInputException;
import com.app.dao.ICustomerRepository;
import com.app.pojos.Customer;

@Service // MANDATORY to tell SC : following is spring bean , containing B.L
@Transactional // annotation meant for SC , to automatically manage txs
public class CustomerServiceImpl implements ICustomerService {

	@Autowired
	ICustomerRepository custRepo;
	
	@Override
	public Customer authenticateCustomer(String email, String password) {
		return custRepo.findByEmailAndPassword(email, password)
				.orElseThrow(() -> new WrongInputException("Invalid Credentials!!!!!"));
	}
	
	@Override
	public Customer insertCustomerDetails(Customer customer) {
		if(customer.getPassword().equals(customer.getConfirmPassword())) {
			return custRepo.save(customer);
		}
		else {
			throw new WrongInputException("Confirm password should be same as password");
		}
	}

	@Override
	public Customer getCustomerDetails(int custId) {
		return custRepo.findById(custId).orElseThrow(()-> new WrongInputException("Customer Id Not Found")) ;
	}
	
	@Override
	public Customer updateCustomerDetails(Customer customer,int custId) {
				custRepo.findById(custId).orElseThrow(()-> new WrongInputException("Customer Id Not Found")) ;
				customer.setId(custId);
				return custRepo.save(customer); 
	}

	@Override
	public Customer updatePassword(String password, int id) {
		Customer customer=custRepo.findById(id).orElseThrow(()-> new WrongInputException("Customer Id Not Found")) ;
		customer.setPassword(password);
		customer.setConfirmPassword(password);
		return custRepo.save(customer);
	}
	
}
