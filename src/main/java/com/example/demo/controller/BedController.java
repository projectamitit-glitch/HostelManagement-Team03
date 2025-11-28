package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.BedDto;
import com.example.demo.service.BedService;

@RestController
public class BedController {
	@Autowired
	BedService bedService;

	@PostMapping("bed/{roomId}")
	public ResponseEntity addBed(@RequestBody BedDto bedDto) {
		bedService.addBed(bedDto);
		return new ResponseEntity("Bed Added", HttpStatus.ACCEPTED);
	}
}
