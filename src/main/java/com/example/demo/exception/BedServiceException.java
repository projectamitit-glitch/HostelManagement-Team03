package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class BedServiceException extends RuntimeException {
	private String errorMassage;
	private HttpStatus httpStatus;

	public BedServiceException(String errorMassage, HttpStatus httpStatus) {
		this.errorMassage = errorMassage;
		this.httpStatus = httpStatus;
	}

	@Override
	public String getMessage() {
		return errorMassage;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	
}
