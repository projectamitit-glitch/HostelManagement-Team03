package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.constant.Constant;
import com.example.demo.dto.BuildingDto;
import com.example.demo.service.BuildingService;
@RestController
public class BuildingController {
	@Autowired
	BuildingService buildingService;
	@PostMapping("building/{hostelId}")
	public ResponseEntity addBuilding(@RequestBody BuildingDto buildingDto,@PathVariable int hostelId) {
		
		buildingService.saveBuilding(buildingDto,hostelId);
		return new ResponseEntity(Constant.BUILDING_SAVED,HttpStatus.CREATED);
	}


}
