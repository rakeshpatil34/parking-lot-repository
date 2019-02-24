package com.parkinglot.exceptions;

import com.parkinglot.entities.User;

public class DuplicateUserException extends Exception {
	private static final long serialVersionUID = 1L;

	public DuplicateUserException(User user) {
		super("User already register with this phone number: "+user.getPhoneNumber());
	}
	
}
