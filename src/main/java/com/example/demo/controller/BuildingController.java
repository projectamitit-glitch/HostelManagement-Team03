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
import com.example.demo.dto.BuildingDto;
import com.example.demo.service.BuildingService;

@RestController
public class BuildingController {
	@Autowired
	BuildingService buildingService;

	@PostMapping("building/{hostelId}")
	public ResponseEntity addBuilding(@RequestBody BuildingDto buildingDto,@PathVariable int hostelId) {
		
		buildingService.saveBuilding(buildingDto,hostelId);
		return new ResponseEntity (Constant.BUILDING_SAVED,HttpStatus.CREATED);
	}

	
	
	@GetMapping("/buildings/{id}")
	public ResponseEntity<BuildingDto> getbuildingbyId(@PathVariable int id) {
	    return new ResponseEntity (buildingService.getBuildingById(id), HttpStatus.OK);
	}

	@GetMapping("/getAllbuildings")
	public ResponseEntity getAllbuildings() {
	    return new ResponseEntity(buildingService.getAllBuildings(), HttpStatus.OK);
	}
	@DeleteMapping("/deletebuilding/{id}")
	public ResponseEntity deletebuildingById(@PathVariable int id) {
	    buildingService.deleteBuildingById(id);
	    return new ResponseEntity("Building Deleted with ID: " + id, HttpStatus.OK);
	}
	@DeleteMapping("/DeleteAllbuildings")
	public ResponseEntity deleteAllbuilding() {
	    buildingService.deleteAllBuildings();
	    return new ResponseEntity("All Building Deleted", HttpStatus.OK);
	}


}
