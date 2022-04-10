package com.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.dao.IUserRepository;
import com.app.pojos.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private IUserRepository userRepo;

	/*
	 * o.s.s.c.userdetails.UserDetails : represents core user information. It stores
	 * user information which is later encapsulated into Authentication object. This
	 * allows non-security related additional user information (eg : email
	 * addresses,telephone numbers ) to be stored in a convenient location.
	 * 
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println("in load user " +email);
		// replaced userName by email
//		User user = userRepo.findByUserName(userName)
//				.orElseThrow(() -> new UsernameNotFoundException("User Name " + userName + " not found!!!"));
		Optional<User> optional = userRepo.findByUserEmail(email);
		User user = optional
				.orElseThrow(() -> new UsernameNotFoundException("User With Email  " +email + " not found!!!"));
		return new CustomUserDetails(user);
	}
	

}
