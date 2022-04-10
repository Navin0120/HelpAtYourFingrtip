package com.app.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.custom_exception.WrongInputException;
import com.app.dao.ICustomerRepository;
import com.app.pojos.Customer;

@Service // MANDATORY to tell SC : following is spring bean , containing B.L
@Transactional // annotation meant for SC , to automatically manage txs
public class CustomerServiceImpl implements ICustomerService {

	@Autowired
	ICustomerRepository custRepo;

	@Override
	public Customer getCustomerDetails(int custId) {
		return custRepo.findById(custId).orElseThrow(() -> new WrongInputException("Customer Not Found"));
	}


	@Override
	public Customer updateCustomerDetails(Customer customer, int custId) {
		custRepo.findById(custId).orElseThrow(() -> new WrongInputException("Customer Id Not Found"));
		customer.setId(custId);
		return custRepo.save(customer);
	}

	@Override
	public Customer getCustomerFromUserId(int id) {
		
		return custRepo.getByUserId(id);
	}
	@Override
	public int getCustomerIdFromUserId(int id) {
		
		return custRepo.getCustomerIdByUserId(id);
	}


}
