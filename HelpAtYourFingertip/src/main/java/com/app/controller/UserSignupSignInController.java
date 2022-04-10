package com.app.controller;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.custom_exception.WrongInputException;
import com.app.dto.AuthenticationRequest;
import com.app.dto.AuthenticationResponse;
import com.app.dto.ChangePassword;
import com.app.dto.CustomerRegDto;
import com.app.dto.TaskerRegDto;
import com.app.jwt_utils.JwtUtils;
import com.app.pojos.Customer;
import com.app.pojos.Tasker;
import com.app.pojos.User;
import com.app.service.CustomUserDetails;
import com.app.service.ICustomerService;
import com.app.service.ITaskerService;
import com.app.service.IUserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserSignupSignInController {

	// auto wire Authentication Manager for user authentication , created in
	// Security Config class
	// (currently based upon user details service)
	@Autowired
	private AuthenticationManager authManager;
	// auto wire JwtUtils for sending signed JWT back to the clnt
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private IUserService userService;
	@Autowired
	private ITaskerService taskerService;
	@Autowired
	private ICustomerService customerService;

	// add end point for customer registration
	@PostMapping("/customer/register")
	public ResponseEntity<?> userRegistration(@RequestBody CustomerRegDto request) {
		System.out.println("in customer reg " + request);
		return ResponseEntity.ok(userService.registerCustomer(request));
	}
	// add end point for tasker registration
	@PostMapping("/tasker/register")
	public ResponseEntity<?> userRegistration(@RequestBody TaskerRegDto request) {
		System.out.println("in tasker reg " + request);
		return ResponseEntity.ok(userService.registerTasker(request));
	}
	// add end point for user authentication
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest request) {
		System.out.println("in auth " + request);
		try {
			// Tries to authenticate the passed Authentication object, returning a fully
			// populated Authentication object (including granted authorities)if successful.
			Authentication authenticate = authManager.authenticate
			// An o.s.s.c.Authentication i/f implementation used for simple presentation of
			// a username and password.
			// Actual dao based authentication takes place here internally(first email : here replaced username by email for authentication
					
			// n then pwd n then authorities gets validated)
			(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
			// => successful authentication : create JWT n send it to the clnt in the
			// response.
			CustomUserDetails userDetails=(CustomUserDetails) authenticate.getPrincipal();
			String role=userDetails.getAuthorities().stream().map(item->item.getAuthority()).collect(Collectors.toList()).get(0);
			System.out.println("auth success " + authenticate);
			int userId=userDetails.getUser().getId();
			int id;
			String firstName;
			String lastName;
			if(role.equals("ROLE_CUSTOMER")) {
				Customer customer=customerService.getCustomerFromUserId(userId);
				id=customer.getId();
				firstName=customer.getFirstName();
				lastName=customer.getLastName();
			}
			else {
				Tasker tasker=taskerService.getTaskerFromUserId(userId);
				id=tasker.getId();
				firstName=tasker.getFirstName();
				lastName=tasker.getLastName();
			}
			return ResponseEntity.ok(new AuthenticationResponse(jwtUtils.generateJwtToken(authenticate),id,firstName,lastName,role));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("User authentication Failed", e);
		}
	}
	
	@PostMapping(value = "/image/{taskerid}")
	public ResponseEntity<?> addNewTasker(@RequestParam MultipartFile imageFile, @PathVariable int taskerid) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(taskerService.insertImage(taskerid, imageFile));
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}
	
	@PatchMapping("/changePassword")
	@PreAuthorize("hasRole('READ_PREVILEDGE')")
	public ResponseEntity<?> changeUserPassword(@RequestBody ChangePassword request)
	{
		User user=userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		if(userService.checkIfValidOldPassword(user, request.getPassword())){
			userService.changeUserPassword(user,request.getNewPassword());
		}
		else {
			throw new WrongInputException("Wrong old Password");
		}
		return ResponseEntity.ok().build();
		
	}

}
