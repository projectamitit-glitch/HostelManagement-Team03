package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.constant.Constant;
import com.example.demo.dto.HostelDto;
import com.example.demo.service.HostelService;

@RestController
public class HostelController {
	
	@Autowired
	HostelService hostelService;
	
	@PostMapping("hostel/{organizationId}")
	public ResponseEntity addHostel(@RequestBody HostelDto hostelDto,@PathVariable int organizationId) {
		
		hostelService.saveHostel(hostelDto,organizationId);
		return new ResponseEntity(Constant.HOSTEL_SAVED ,HttpStatus.CREATED);
	}
	
	@GetMapping("hostel/{id}")
	public ResponseEntity getHostel(@PathVariable int id) {
	HostelDto hostelDto = 	hostelService.getHostel(id);
		return new ResponseEntity(hostelDto,HttpStatus.OK);
	}
	
	@GetMapping("hostels")
	public ResponseEntity getAllHostels() {
		List<HostelDto> hostelDtos = hostelService.getAllHostels();
		return new ResponseEntity(hostelDtos,HttpStatus.OK);
	}
	
	@DeleteMapping("hostel/{id}")
	public ResponseEntity deleteHostel(@PathVariable int id) {
		hostelService.deleteHostel(id);
		return new ResponseEntity(Constant.HOSTEL_DELETED,HttpStatus.OK);
	}
	
	@DeleteMapping("hostels")
	public ResponseEntity deleteAllHostels() {
		hostelService.deleteAllHostels();
		return new ResponseEntity(Constant.ALL_HOSTELS_DELETED,HttpStatus.OK);
	}
	
	@GetMapping("searchHostel")
	public ResponseEntity searchHostel(@RequestParam(required = false) String city,@RequestParam(required = false) String area) {
		List<HostelDto> l=hostelService.searchHostel(city, area);
		return new ResponseEntity(l,HttpStatus.OK);
		
	}
	
	
	

}
