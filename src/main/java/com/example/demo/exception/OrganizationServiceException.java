package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class OrganizationServiceException extends RuntimeException {

	private final String errorMessage;
	private final HttpStatus httpStatus;

	public OrganizationServiceException(String errorMessage, HttpStatus httpStatus) {
		this.errorMessage = errorMessage;
		this.httpStatus = httpStatus;
	}

	@Override
	public String getMessage() {
		return errorMessage;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
