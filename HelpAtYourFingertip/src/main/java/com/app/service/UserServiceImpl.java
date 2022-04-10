package com.app.service;

import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.dao.ICustomerRepository;
import com.app.dao.ITaskerRepository;
import com.app.dao.IUserRepository;
import com.app.dao.RoleRepository;
import com.app.dto.CustomerRegDto;
import com.app.dto.TaskerRegDto;
import com.app.pojos.Customer;
import com.app.pojos.Role;
import com.app.pojos.Tasker;
import com.app.pojos.User;
import com.app.pojos.UserRoles;

@Service
@Transactional
public class UserServiceImpl implements IUserService {
	@Autowired
	private IUserRepository userRepo;
	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private ICustomerRepository custRepo;
	@Autowired
	private ITaskerRepository taskerRepo;
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public Customer registerCustomer(CustomerRegDto request) {
		// create User from request payload
		/*
		 * { "userName": "Rama", "email": "rama@gmail.com", "password": "ram#12345",
		 * "roles": [ "ROLE_ADMIN" ] }
		 */
		User user = new User();
		user.setEmail(request.getEmail());
		user.setPassword(encoder.encode(request.getPassword()));//set encoded pwd
		Set<Role> role=request.getRoles().stream().
				map(roleName -> roleRepo.findByUserRole(UserRoles.valueOf(roleName)).get())
				.collect(Collectors.toSet());
				//convert Set<String> : role names ---> Stream<String>
		//mapping roleName --> Role (using RoleRepo) 		
				//collect in a Set<Role>
				
		user.setRoles(role);
		user.setActive(true);
		User persistentUser = userRepo.save(user);//persisted user details in db
		Customer dto = new Customer(persistentUser,request.getFirstName(),request.getLastName(),request.getAddress(),request.getAadharNo(),request.getContactNo());
		//BeanUtils.copyProperties(persistentUser, dto);//for sending resp : copied User--->User resp DTO
		custRepo.save(dto);
		return dto;
	}
	@Override
	public Tasker registerTasker(TaskerRegDto request) {
		// create User from request payload
		/*
		 * { "userName": "Rama", "email": "rama@gmail.com", "password": "ram#12345",
		 * "roles": [ "ROLE_ADMIN" ] }
		 */
		User user = new User();
		user.setEmail(request.getEmail());
		user.setPassword(encoder.encode(request.getPassword()));//set encoded pwd
		Set<Role>role=request.getRoles().stream().
				map(roleName -> roleRepo.findByUserRole(UserRoles.valueOf(roleName)).get())
				.collect(Collectors.toSet());
		user.setRoles(role);
		user.setActive(true);
		User persistentUser = userRepo.save(user);//persisted user details in db

		Tasker dto = new Tasker(persistentUser,request.getFirstName(),request.getLastName(),
				request.getAadharNo(),request.getCity(),request.getContactNo(),0,request.getBio(),null,request.getServices());
		//BeanUtils.copyProperties(persistentUser, dto);//for sending resp : copied User--->User resp DTO
		taskerRepo.save(dto);
		return dto;
	}
	@Override
	public void changePassword(String email,String password) {
		userRepo.findByUserEmail(email).orElseThrow(()->new RuntimeException()).setPassword(encoder.encode(password)); ;//set encoded pwd
		return;
	}
	@Override
	public User findUserByEmail(String email) {
		return userRepo.findByUserEmail(email).orElseThrow(()->new RuntimeException());//set encoded pwd
		
	}
	@Override
	public boolean checkIfValidOldPassword(User user,String oldPassword) {
		
		return encoder.matches(oldPassword, user.getPassword());
	}
	@Override
	public void changeUserPassword(User user,String newPassword) {
		user.setPassword(encoder.encode(newPassword));
		userRepo.save(user);
		
	}

}
