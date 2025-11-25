package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.OrganizationDto;
import com.example.demo.service.OrganizationService;

@RestController
public class OrganizationController {
	
	@Autowired
	OrganizationService organizationService;
	
	@PostMapping("organization")
	ResponseEntity addOrganization(@RequestBody OrganizationDto organizationDto) {
		organizationService.addOrganization(organizationDto);
		return new ResponseEntity("Organization Added", HttpStatus.ACCEPTED);
		
	}

}
