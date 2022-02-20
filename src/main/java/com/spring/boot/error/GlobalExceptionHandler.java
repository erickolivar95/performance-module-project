package com.spring.boot.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;





@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<ErrorDTO> handleException(IncorrectBodyException exc) {
		ErrorDTO error = new ErrorDTO(HttpStatus.BAD_REQUEST.value(), exc.getMessage());
		return new ResponseEntity<ErrorDTO>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorDTO> handleException(UserNotAuthorizedException exc) {
		ErrorDTO error = new ErrorDTO(HttpStatus.FORBIDDEN.value(), exc.getMessage());
		return new ResponseEntity<ErrorDTO>(error, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorDTO> handleException(ItemNotFoundException exc) {
		ErrorDTO error = new ErrorDTO(HttpStatus.NOT_FOUND.value(), exc.getMessage());
		return new ResponseEntity<ErrorDTO>(error, HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler
	public ResponseEntity<ErrorDTO> handleException(NumberFormatException exc) {
		ErrorDTO error = new ErrorDTO(HttpStatus.BAD_REQUEST.value(), "A text was entered instead of a number");
		return new ResponseEntity<ErrorDTO>(error, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler
	public ResponseEntity<ErrorDTO> handleException(NullPointerException exc) {
		ErrorDTO error = new ErrorDTO(HttpStatus.BAD_REQUEST.value(), "You have to create the content first");
		return new ResponseEntity<ErrorDTO>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorDTO> handleException(InvalidFormatException exc) {
		ErrorDTO error = new ErrorDTO(HttpStatus.BAD_REQUEST.value(), "You have entered an invalid value in the body request");
		return new ResponseEntity<ErrorDTO>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorDTO> handleException(IllegalArgumentException exc) {
		ErrorDTO error = new ErrorDTO(HttpStatus.BAD_REQUEST.value(), "Invalid date to search, it must be a date with the format yyyy-MM-dd");
		return new ResponseEntity<ErrorDTO>(error, HttpStatus.BAD_REQUEST);
	}
	
}
