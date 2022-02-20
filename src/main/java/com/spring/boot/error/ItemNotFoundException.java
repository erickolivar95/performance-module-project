package com.spring.boot.error;

public class ItemNotFoundException extends RuntimeException {

	public ItemNotFoundException(String message) {
		super(message);
	}
	
}
