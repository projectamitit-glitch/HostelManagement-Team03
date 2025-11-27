package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.RoomDto;
import com.example.demo.exception.RoomServiceException;
import com.example.demo.service.RoomService;

@RestController
public class RoomController {
	
	@Autowired
	RoomService roomService;
	
	@PostMapping("room/{floorId}")
	public ResponseEntity saveRoom(@RequestBody RoomDto roomDto,@PathVariable int floorId) {
		
		try{
			
		roomService.addRoom(roomDto, floorId);
		return new  ResponseEntity("Room Added",HttpStatus.CREATED);
		
		}catch(RoomServiceException e) {
			return new ResponseEntity(e.getMessage(),e.getHttpStatus());
		}
	}

}
