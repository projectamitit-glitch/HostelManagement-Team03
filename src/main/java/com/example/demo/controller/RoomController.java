package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.constant.Constant;
import com.example.demo.dto.RoomDto;
import com.example.demo.service.RoomService;

@RestController
public class RoomController {

	@Autowired
	RoomService roomService;

	@PostMapping("room/{floorId}")
	public ResponseEntity saveRoom(@RequestBody RoomDto roomDto, @PathVariable int floorId) {

		roomService.addRoom(roomDto, floorId);
		return new ResponseEntity(Constant.ROOM_SAVED, HttpStatus.CREATED);

	}
	
	@GetMapping("room/{id}")
	public ResponseEntity getRoom(@PathVariable int id) {
	RoomDto roomDto = 	roomService.getRoom(id);
	return new ResponseEntity (roomDto,HttpStatus.OK);
		
	}
	
	@GetMapping("rooms")
	public ResponseEntity getAllRoom() {
	List<RoomDto> roomDtoList =	roomService.getAllRooms();
	return new ResponseEntity(roomDtoList,HttpStatus.OK);
	}
	
	@DeleteMapping("/room/{id}")
	public  ResponseEntity deleteRoom(@PathVariable int id) {
		roomService.deleteRoom(id);
		return new ResponseEntity(Constant.ROOM_DELETED,HttpStatus.OK);
	}
	
	@DeleteMapping("rooms")
	public ResponseEntity deleteAllRoom() {
		roomService.deleteAll();
		return  new ResponseEntity(Constant.ALL_ROOMS_DELETED,HttpStatus.OK);
	}
	

}
