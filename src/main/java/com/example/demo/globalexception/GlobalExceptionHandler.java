package com.example.demo.globalexception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.exception.HostelManagementException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(exception = HostelManagementException.class)
	public ResponseEntity<String> handleHostelException(HostelManagementException managementException) {
		return new ResponseEntity<String>(managementException.getErrorMessage(), managementException.getHttpStatus());
	}

	@ExceptionHandler(exception = Exception.class)
	public ResponseEntity<String> handleGlobalException(Exception exception) {
		return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
