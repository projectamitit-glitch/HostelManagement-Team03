package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class HostelServiceException extends RuntimeException{
	
	private String errorMessage;
	private HttpStatus httpStatus;
	
	
	
	@Override
	public String getMessage() {
		return errorMessage;
	}



	public HttpStatus getHttpStatus() {
		return httpStatus;
	}



	public String getErrorMessage() {
		return errorMessage;
	}



	public HostelServiceException(String errorMessage, HttpStatus httpStatus) {
		
		this.errorMessage = errorMessage;
		this.httpStatus = httpStatus;
	}
	
	
	
	
	

}
