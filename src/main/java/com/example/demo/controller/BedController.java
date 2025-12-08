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
import com.example.demo.dto.BedDto;
import com.example.demo.service.BedService;

@RestController
public class BedController {
	@Autowired
	BedService bedService;

	@PostMapping("bed/{roomId}")
	public ResponseEntity<String> addBed(@PathVariable int roomId, @RequestBody BedDto bedDto) {
		bedService.addBed(roomId, bedDto);
		return new ResponseEntity<>(Constant.BED_SAVED, HttpStatus.CREATED);
	}

	@GetMapping("getAllBeds")
	public List<BedDto> getAllBeds() {
		return bedService.getAllBeds();
	}

	@GetMapping("getBedById/{id}")
	public BedDto getBedById(@PathVariable int id) {
		return bedService.getBedById(id);
	}

	@DeleteMapping("deleteAllBeds")
	public ResponseEntity<String> deleteAllBeds() {
		bedService.deleteAllBeds();
		return new ResponseEntity<>(Constant.ALL_BEDS_DELETED, HttpStatus.OK);
	}

	@DeleteMapping("deleteBedById/{id}")
	public ResponseEntity<String> deleteBedById(@PathVariable int id) {
		bedService.deleteBedById(id);
		return new ResponseEntity<>(Constant.BED_DELETED, HttpStatus.OK);
	}

	@GetMapping("getAvailableBedsByRoomSharing/{hostelId}/{sharing}")
	List<BedDto> getAvailableBedsByRoomSharing(@PathVariable int hostelId, @PathVariable int sharing) {
		return bedService.getAvailableBedsByRoomSharing(hostelId, sharing);
	}

}
