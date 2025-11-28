package com.example.demo.globalexception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.example.demo.exception.FloorServiceException;
import com.example.demo.exception.BedServiceException;
import com.example.demo.exception.BuildingServiceException;

import com.example.demo.exception.RoomServiceException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(exception = Exception.class)
	public ResponseEntity<String> handleGlobalException(Exception exception) {
		return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
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
	public ResponseEntity<String> handleBedServiceException(BedServiceException handleBedServiceException) {
		return new ResponseEntity<String>(handleBedServiceException.getMessage(),
				handleBedServiceException.getHttpStatus());
	}

}
