package com.app.custom_exception;

public class WrongInputException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WrongInputException(String message)
	{
		super(message);
	}
}
