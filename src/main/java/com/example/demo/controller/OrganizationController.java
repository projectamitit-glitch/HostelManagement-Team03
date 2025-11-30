package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		return new ResponseEntity("Organization Added", HttpStatus.CREATED);

	}

	@GetMapping("organization/{id}")
	public ResponseEntity getOrganizationById(@PathVariable int id) {
		return new ResponseEntity<>(organizationService.getOrganizationById(id), HttpStatus.OK);
	}

	@GetMapping("organizations")
	public ResponseEntity getAllOrganizations() {
		return new ResponseEntity<>(organizationService.getAllOrganizations(), HttpStatus.OK);
	}

	@DeleteMapping("organization/{id}")
	public ResponseEntity deleteOrganizationById(@PathVariable int id) {
		organizationService.deleteOrganizationById(id);
		return new ResponseEntity<>("Organization Deleted with ID: " + id, HttpStatus.OK);
	}

	@DeleteMapping("organizations")
	public ResponseEntity deleteAllOrganizations() {
		organizationService.deleteAllOrganizations();
		return new ResponseEntity<>("All Organizations Deleted", HttpStatus.OK);
	}

}
