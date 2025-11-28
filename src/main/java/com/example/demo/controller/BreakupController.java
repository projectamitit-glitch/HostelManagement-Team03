package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.BreakupDto;
import com.example.demo.service.BreakupService;

@RestController
public class BreakupController {
	@Autowired
	BreakupService breakupService;

	@GetMapping("getinvoice/{id}/{duration}")
	public ResponseEntity<BreakupDto> getInvoice(@PathVariable int id, @PathVariable int duration) {
		BreakupDto breakupDto = breakupService.getByBedIdAndDuration(id, duration);
		return new ResponseEntity<BreakupDto>(breakupDto, HttpStatus.OK);
	}

}
