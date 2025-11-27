package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dto.FloorDto;
import com.example.demo.service.FloorService;

@RestController
public class FloorController {

	@Autowired
	FloorService floorService;

	@PostMapping("floor/{buildingID}")
	ResponseEntity<String> addFloor(@RequestBody FloorDto floordto, @PathVariable int buildingID) {
		floorService.saveFloor(floordto, buildingID);
		return new ResponseEntity<>("floor added", HttpStatus.CREATED);
	}
}
