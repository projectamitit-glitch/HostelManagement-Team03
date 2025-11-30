package com.example.demo.exception;
import org.springframework.http.HttpStatus;
public class OrganizationServiceException extends RuntimeException {

	private String errorMessage;
	private HttpStatus httpStatus;
	 public OrganizationServiceException(String errorMessage,HttpStatus httpStatus) {
	        this.errorMessage = errorMessage;
	        this.httpStatus = HttpStatus.BAD_REQUEST;  
	    }
	@Override
	public String getMessage() {
		return this.errorMessage;
	}

	public HttpStatus getHttpStatus() {
		return this.httpStatus;
	}
}
