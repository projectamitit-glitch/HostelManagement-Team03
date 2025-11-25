package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.Building;
import com.example.demo.service.BuildingService;

@RestController
public class BuildingController {

	@Autowired
	BuildingService buildingService;

	@PostMapping("/addBuilding")
	public ResponseEntity<Building> addBuilding(@RequestBody Building building) {
		return ResponseEntity.ok(buildingService.addBuilding(building));
	}

	@GetMapping("/getBuildings")
	public ResponseEntity<List<Building>> getAllBuildings() {
		return ResponseEntity.ok(buildingService.getAllBuildings());
	}

	@GetMapping("/getBuildingById/{id}")
	public ResponseEntity<Building> getBuildingById(@PathVariable int id) {
		return ResponseEntity.ok(buildingService.getBuildingById(id));
	}

	@GetMapping("/getBuildingByName/{name}")
	public ResponseEntity<Building> getBuildingByName(@PathVariable String name) {
		return ResponseEntity.ok(buildingService.getBuildingByName(name));
	}

	@PostMapping("/addAllBuildings")
	public ResponseEntity<List<Building>> addAllBuildings(@RequestBody List<Building> buildings) {
		return ResponseEntity.ok(buildingService.addAllBuildings(buildings));
	}

	@GetMapping("/getWardenByBuildingName/{name}")
	public ResponseEntity<String> getWardenByBuildingName(@PathVariable String name) {
		return ResponseEntity.ok(buildingService.getWardenByBuildingName(name));
	}

	@DeleteMapping("/deleteByid/{id}")
	public ResponseEntity<Building> deleteBuildingById(@PathVariable int id) {
		return (ResponseEntity<Building>) ResponseEntity.ok();
	}

}
