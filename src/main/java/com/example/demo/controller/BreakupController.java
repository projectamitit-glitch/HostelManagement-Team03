package com.example.demo.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.BreakupDto;
import com.example.demo.service.BreakupService;

@RestController
public class BreakupController {

    @Autowired
	BreakupService breakupService;

    @GetMapping("invoice/{bedId}")
	public ResponseEntity<BreakupDto> getBreakup(@PathVariable int bedId,@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,@RequestParam(defaultValue = "1") int duration) {
		
	            BreakupDto breakupDto = breakupService.getBreakup(bedId, startDate, duration);
	            return new ResponseEntity(breakupDto,HttpStatus.OK);
		
	}
}
