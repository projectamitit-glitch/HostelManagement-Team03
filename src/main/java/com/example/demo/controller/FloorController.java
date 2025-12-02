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
import com.example.demo.dto.FloorDto;
import com.example.demo.service.FloorService;

@RestController
public class FloorController {

	@Autowired
	FloorService floorService;

	@PostMapping("floor/{buildingID}")
	ResponseEntity<String> addFloor(@RequestBody FloorDto floordto, @PathVariable int buildingID) {
		floorService.saveFloor(floordto, buildingID);
		return new ResponseEntity<String>(Constant.FLOOR_SAVED, HttpStatus.CREATED);
	}
	@GetMapping("floor/{floorId}")
	ResponseEntity<FloorDto> getFloor(@PathVariable int floorId ){
	FloorDto floorDto=	floorService.getFloor(floorId);
		return new ResponseEntity<>(floorDto,HttpStatus.OK);
	}
	 @GetMapping("floors")
	public ResponseEntity<List<FloorDto>> getFloors(){
List<FloorDto> floorDtos=	floorService.getFloors();
		return new ResponseEntity<>(floorDtos,HttpStatus.OK);
	}
	@DeleteMapping("floor/{floorId}/{buildingId}")
	public ResponseEntity<String> deleteFloor(@PathVariable int floorId ,@PathVariable int buildingId ) {
		floorService.deleteFloor(floorId,buildingId);
		 return new ResponseEntity<String>(Constant.FLOOR_DELETED, HttpStatus.OK);
	}
	
	@DeleteMapping("floors")
	public ResponseEntity<String> deleteFloors(){
		floorService.deleteFloors();
		return new ResponseEntity<String>(Constant.FLOORS_DELETED, HttpStatus.OK);
	}
	
}

