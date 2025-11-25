package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Hostel;
import com.example.demo.Entity.Organization;
import com.example.demo.dto.HostelDto;
import com.example.demo.repository.HostelRepository;
import com.example.demo.repository.OrganizationRepository;

@Service
public class HostelServiceImpl implements HostelService {
	
	@Autowired
	HostelRepository hostelRepository;
	
	@Autowired
	OrganizationRepository organizationRepository;

	@Override
	public void saveHostel(HostelDto hostelDto,int organizationId) {
		
		Hostel hostel = new Hostel();
		hostel.setName(hostelDto.getName());
		hostel.setAddress(hostelDto.getAddress());
		hostel.setCapacity(hostelDto.getCapacity());
		hostel.setContactNo(hostelDto.getContactNo());
		hostel.setImage(hostelDto.getImage());
		hostel.setWebsite(hostelDto.getWebsite());
		hostel.setType(hostelDto.getType());
		
		Organization organization = organizationRepository.findById(organizationId).get();
		
		hostel.setOrganization(organization);
		hostelRepository.save(hostel);
		
		
		
		
	}

}
