package com.app.service;

import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.custom_exception.WrongInputException;
import com.app.dao.ICustomerRepository;
import com.app.dao.IRoleRepository;
import com.app.dao.ITaskerRepository;
import com.app.dao.IUserRepository;
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
	private IRoleRepository roleRepo;
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
		Customer dto;
		try {
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
		String firstName=request.getFirstName().strip().substring(0,1).toUpperCase().concat(request.getFirstName().strip().substring(1));
		String lastName=request.getLastName().strip().substring(0,1).toUpperCase().concat(request.getLastName().strip().substring(1));
		dto = new Customer(persistentUser,firstName,lastName,request.getAddress(),request.getAadharNo(),request.getContactNo());
		//BeanUtils.copyProperties(persistentUser, dto);//for sending resp : copied User--->User resp DTO
		custRepo.save(dto);
		}
		catch(DataIntegrityViolationException e) {
			System.out.println("error");
			throw new WrongInputException("Email Already Exists");
		}
		return dto;
	}
	@Override
	public Tasker registerTasker(TaskerRegDto request) {
		// create User from request payload
		/*
		 * { "userName": "Rama", "email": "rama@gmail.com", "password": "ram#12345",
		 * "roles": [ "ROLE_ADMIN" ] }
		 */
		Tasker dto;
		try {
		User user = new User();
		user.setEmail(request.getEmail());
		user.setPassword(encoder.encode(request.getPassword()));//set encoded pwd
		Set<Role>role=request.getRoles().stream().
				map(roleName -> roleRepo.findByUserRole(UserRoles.valueOf(roleName)).get())
				.collect(Collectors.toSet());
		user.setRoles(role);
		user.setActive(true);
		User persistentUser = userRepo.save(user);//persisted user details in db
		String firstName=request.getFirstName().strip().substring(0,1).toUpperCase().concat(request.getFirstName().strip().substring(1));
		String lastName=request.getLastName().strip().substring(0,1).toUpperCase().concat(request.getLastName().strip().substring(1));
		dto = new Tasker(persistentUser,firstName,lastName,
				request.getAadharNo(),request.getCity(),request.getContactNo(),0,request.getBio(),null,request.getServices());
		//BeanUtils.copyProperties(persistentUser, dto);//for sending resp : copied User--->User resp DTO
		taskerRepo.save(dto);
		}
		catch(DataIntegrityViolationException e) {
			System.out.println("error");
			throw new WrongInputException("Email Already Exists");
		}
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
