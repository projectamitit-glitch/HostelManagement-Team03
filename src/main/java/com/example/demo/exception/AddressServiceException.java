package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class AddressServiceException extends RuntimeException {
	private String errorMessage;
	private HttpStatus httpStatus;

	@Override
	public String getMessage() {
		return errorMessage;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public AddressServiceException(String errorMessage, HttpStatus httpStatus) {
		super();
		this.errorMessage = errorMessage;
		this.httpStatus = httpStatus;
	}

	

}
