package com.parkinglot.exceptions;

public class UserNotFoundException extends Exception {
private static final long serialVersionUID = 1L;
	
	public UserNotFoundException(Long id) {
		super("User not found: "+id);
	}

}
