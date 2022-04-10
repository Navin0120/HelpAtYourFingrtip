package com.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.pojos.Customer;

public interface ICustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("select u from Customer u where u.customer.id=?1")
	Customer getByUserId(int id);
	@Query("select u.id from Customer u where u.customer.id=?1")
	int getCustomerIdByUserId(int id);
	
}
