package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class OrganizationServiceException extends RuntimeException {

	private String errorMassage;
	private HttpStatus httpStatus;

	public OrganizationServiceException(String errorMassage, HttpStatus httpStatus) {
		this.errorMassage = errorMassage;
		this.httpStatus = httpStatus;
	}

	@Override
	public String getMessage() {
		return errorMassage;
	}

	private String errorMessage;
	private HttpStatus httpStatus;
	
	
	
	@Override
	public String getMessage() {
		return errorMessage;
	}



	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}



	public String getErrorMessage() {
		return errorMessage;
	}



	public OrganizationServiceException(String errorMessage, HttpStatus httpStatus) {
		
		this.errorMessage = errorMessage;
		this.httpStatus = httpStatus;
	}
	
	
	
}
