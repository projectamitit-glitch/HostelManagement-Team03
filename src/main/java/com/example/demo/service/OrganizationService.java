package com.example.demo.service;

import java.util.List;

import com.example.demo.Entity.Organization;
import com.example.demo.dto.OrganizationDto;

public interface OrganizationService {

	void addOrganization(OrganizationDto organizationDto);
	
	 OrganizationDto getOrganizationById(int id);
	
	 List<OrganizationDto> getAllOrganizations();
	 
	 void deleteOrganizationById(int id);
	 
	 void deleteAllOrganizations();
	
}


