package com.app.exc_handler;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.app.custom_exceptions.WrongInputException;
import com.app.dto.ErrorResponse;

@ControllerAdvice // Mandatory annotation to tell SC , following class will contain 
				 //  common advice on exception handling for all controllers
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	//ResponseEntityExceptionHandler -> A convenient base class for @ControllerAdvice classes that wish to provide 
	//centralized exception handling across all @RequestMapping methods through @ExceptionHandler methods

	//Handle validation related errors globally
	//ResponseEntityExceptionHandler.class => @ExceptionHandler(MethodArgumentNotValidException.class1)
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		//return error mesgs : field Name : err mesg
		Map<String, String> map = ex.getFieldErrors().stream() //Stream<FieldError>
		.collect(Collectors.toMap(FieldError::getField,FieldError::getDefaultMessage));
		return ResponseEntity.badRequest().body(map);
	}
	//Exception 		--> checked --> force to handle
	//RuntimeException	--> unchecked --> not forced to handle
	//how to deal with custom exc globally ? 
	//how to tell SC , following is an exc handling method
	//method arg : Class<T> : T => type of the exception , to be handled

	//equivalent catch-all block
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<?> handleRuntimeException(RuntimeException e)
	{
		ErrorResponse resp=new ErrorResponse(e.getMessage(),LocalDateTime.now());
		//dto to create error response
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
	}	
	//INTERNAL_SERVER_ERROR => 500 => server side issue eg. permission error,3rd party plugin not available 
	//BAD_REQUEST => 400 => bad client request => eg.id not found
	//CREATED => 201
	//OK => 200
	//NOT_FOUND => 404
	//ACCEPTED => 202
	//NO_CONTENT => 204

	/*	Informational responses (100–199)
		Successful responses (200–299)
		Redirection messages (300–399)
		Client error responses (400–499)
		Server error responses (500–599) */
	
	@ExceptionHandler(WrongInputException.class)
	public ResponseEntity<?> handleWongInputException(RuntimeException e)
	{
		ErrorResponse resp=new ErrorResponse(e.getMessage(),LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
	}	
	
	
}
