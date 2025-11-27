package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class HostelManagementException extends RuntimeException {
	private String errorMessage;
	private HttpStatus httpStatus;

	@Override
	public String getMessage() {
		return this.errorMessage;
	}

	public HttpStatus getHttpStatus() {
		return this.httpStatus;
	}

	public HostelManagementException(String errorMessage, HttpStatus httpStatus) {
		this.errorMessage = errorMessage;
		this.httpStatus = httpStatus;
	}

}