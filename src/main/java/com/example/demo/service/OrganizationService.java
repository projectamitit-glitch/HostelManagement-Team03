package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.OrganizationDto;

public interface OrganizationService {

	void addOrganization(OrganizationDto organizationDto);
	
	 List<OrganizationDto> getAllOrganizations();

	 OrganizationDto getOrganizationById(int id);
	 
	 void deleteOrganizationById(int id);
	 
	 void deleteAllOrganizations();
	
}
