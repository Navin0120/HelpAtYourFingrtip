package com.app.custom_exceptions;

public class WongInputException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WongInputException(String message)
	{
		super(message);
	}
}
