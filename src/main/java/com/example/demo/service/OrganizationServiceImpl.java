package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Organization;
import com.example.demo.dto.OrganizationDto;
import com.example.demo.repository.OrganizationRepository;

@Service
public class OrganizationServiceImpl implements OrganizationService{
	
	@Autowired
	OrganizationRepository organizationRepository;

	@Override
	public void addOrganization(OrganizationDto organizationDto) {
		// TODO Auto-generated method stub
		Organization organization=new Organization();
		organization.setAddress(organizationDto.getAddress());
		organization.setContactNo(organizationDto.getContactNo());
		organization.setEmail(organizationDto.getEmail());
		organization.setName(organizationDto.getName());
		organization.setOwnerName(organizationDto.getOwnerName());
		organizationRepository.save(organization);	
	}

}
