package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class FloorServiceException extends RuntimeException {
	private String errorMessage;
	private HttpStatus httpStatus;

	public FloorServiceException(String errorMessage, HttpStatus httpStatus) {
		this.errorMessage = errorMessage;
		this.httpStatus = httpStatus;
	}

	@Override
	public String getMessage() {
		return this.errorMessage;
	}

	public HttpStatus getHttpStatus() {
		return this.httpStatus;
	}
}
