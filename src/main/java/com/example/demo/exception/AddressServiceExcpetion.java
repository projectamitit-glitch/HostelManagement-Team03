package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class AddressServiceExcpetion extends RuntimeException {
	private String errorMessage;
	private HttpStatus httpStatus;

	@Override
	public String getMessage() {
		return errorMessage;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public AddressServiceExcpetion(String errorMessage, HttpStatus httpStatus) {
		super();
		this.errorMessage = errorMessage;
		this.httpStatus = httpStatus;
	}

	

}
