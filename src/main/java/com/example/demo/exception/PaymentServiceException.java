package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class PaymentServiceException extends RuntimeException{

	private String errorMassage;
	private HttpStatus httpStatus;

	public PaymentServiceException(String errorMassage, HttpStatus httpStatus) {
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

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
}

