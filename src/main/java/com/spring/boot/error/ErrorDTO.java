package com.spring.boot.error;

public class ErrorDTO {

	private int status;
	private String message;
	
	
	public ErrorDTO(int status, String message) {
		this.status = status;
		this.message = message;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int statussss) {
		this.status = statussss;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
