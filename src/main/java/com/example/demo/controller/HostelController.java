package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.constant.Constant;
import com.example.demo.dto.HostelDto;
import com.example.demo.service.HostelService;

@RestController
public class HostelController {
	
	@Autowired
	HostelService hostelService;
	
	@PostMapping("hostel/{organizationId}")
	public ResponseEntity addHotel(@RequestBody HostelDto hostelDto,@PathVariable int organizationId) {
		
		hostelService.saveHostel(hostelDto,organizationId);
		return new ResponseEntity(Constant.HOSTEL_SAVED ,HttpStatus.CREATED);
	}

}
