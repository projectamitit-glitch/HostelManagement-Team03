package com.example.demo.globalexception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.example.demo.exception.FloorServiceException;
import com.example.demo.exception.HostelServiceException;
import com.example.demo.exception.OraganizationServiceException;
import com.example.demo.exception.BedServiceException;
import com.example.demo.exception.BreakupServiceException;
import com.example.demo.exception.BuildingServiceException;

import com.example.demo.exception.RoomServiceException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(exception = Exception.class)
	public ResponseEntity<String> handleGlobalException(Exception exception) {
		return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(exception = OraganizationServiceException.class)
	public ResponseEntity<String> handleOraganizationServiceException(
			OraganizationServiceException OraganizationServiceException) {
		return new ResponseEntity<String>(OraganizationServiceException.getMessage(),
				OraganizationServiceException.getHttpStatus());
	}

	@ExceptionHandler(exception = BuildingServiceException.class)
	public ResponseEntity<String> handleBuildingServiceException(
			BuildingServiceException handleBuildingServiceException) {
		return new ResponseEntity<String>(handleBuildingServiceException.getMessage(),
				handleBuildingServiceException.getHttpStatus());
	}

	@ExceptionHandler(exception = FloorServiceException.class)
	public ResponseEntity<String> handleFloorServiceException(FloorServiceException floorServiceException) {
		return new ResponseEntity<String>(floorServiceException.getMessage(), floorServiceException.getHttpStatus());
	}

	@ExceptionHandler(exception = RoomServiceException.class)
	public ResponseEntity<String> handleRoomServiceException(RoomServiceException roomServiceException) {
		return new ResponseEntity<String>(roomServiceException.getMessage(), roomServiceException.getHttpStatus());

	}

	@ExceptionHandler(exception = BedServiceException.class)
	public ResponseEntity<String> handleBedServiceException(BedServiceException BedServiceException) {
		return new ResponseEntity<String>(BedServiceException.getMessage(), BedServiceException.getHttpStatus());
	}

	@ExceptionHandler(exception = BreakupServiceException.class)
	public ResponseEntity<String> handleBreakupServiceException(BreakupServiceException breakupServiceException) {
		return new ResponseEntity<String>(breakupServiceException.getMessage(),
				breakupServiceException.getHttpStatus());
	}

	@ExceptionHandler(exception = HostelServiceException.class)
	public ResponseEntity<String> handleHostelServiceException(HostelServiceException hostelServiceException) {
		return new ResponseEntity<String>(hostelServiceException.getMessage(), hostelServiceException.getHttpStatus());
	}

}
