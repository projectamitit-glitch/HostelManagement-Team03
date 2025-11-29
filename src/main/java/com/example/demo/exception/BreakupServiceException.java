package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class BreakupServiceException extends RuntimeException {
	private String errorMessage;
	private HttpStatus httpStatus;

	@Override
	public String getMessage() {
		return super.getMessage();
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public BreakupServiceException(String errorMessage, HttpStatus httpStatus) {
		super();
		this.errorMessage = errorMessage;
		this.httpStatus = httpStatus;
	}

}
