package com.example.demo.exception;
import org.springframework.http.HttpStatus;
public class OrganizationServiceException extends RuntimeException {

    private String errorMessage;
    private HttpStatus httpStatus;
    public OrganizationServiceException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.httpStatus = HttpStatus.BAD_REQUEST; 
    }
    public OrganizationServiceException(String errorMessage, HttpStatus httpStatus) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
