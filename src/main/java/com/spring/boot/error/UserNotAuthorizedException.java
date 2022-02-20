package com.spring.boot.error;

public class UserNotAuthorizedException extends RuntimeException {

	public UserNotAuthorizedException(String message) {
		super(message);
	}

	
	
}
