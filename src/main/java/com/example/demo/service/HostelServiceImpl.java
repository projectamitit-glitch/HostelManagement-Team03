package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Hostel;
import com.example.demo.Entity.Organization;
import com.example.demo.constant.ErrorConstant;
import com.example.demo.dto.HostelDto;
import com.example.demo.exception.HostelServiceException;
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
		
		
		
		Organization organization = organizationRepository.findById(organizationId).get();
		
		if(organization==null) {
			
		}
		
		Hostel hostel = new Hostel();
		hostel.setOrganization(organization);
		hostel.setName(hostelDto.getName());
		hostel.setAddress(hostelDto.getAddress());
		hostel.setCapacity(hostelDto.getCapacity());
		hostel.setContactNo(hostelDto.getContactNo());
		hostel.setImage(hostelDto.getImage());
		hostel.setWebsite(hostelDto.getWebsite());
		hostel.setType(hostelDto.getType());
		
	
		hostelRepository.save(hostel);
		
		
		
		
	}

	@Override
	public void deleteHostel(int id) {
	Hostel hostel = 	hostelRepository.findById(id).get();
	if(hostel==null) {
		throw new HostelServiceException(ErrorConstant.HOSTEL_NOT_FOUND, HttpStatus.NOT_FOUND);
	}
	
		
		hostelRepository.delete(	hostel);
		
	}

	@Override
	public void deleteAllHostels() {
		List<Hostel> hostels = hostelRepository.findAll();
		if(hostels.isEmpty()) {
			throw new HostelServiceException(ErrorConstant.HOSTEL_DELETE_ALL_EXCEPTION, HttpStatus.NO_CONTENT);
		}
		hostelRepository.deleteAll(hostels);
		
	}

	@Override
	public HostelDto getHostel(int id) {
		Hostel hostel = hostelRepository.findById(id).get();
		if(hostel==null) {
			throw new HostelServiceException(ErrorConstant.HOSTEL_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
		HostelDto hostelDto = new HostelDto();
		hostelDto.setAddress(hostel.getAddress());
		hostelDto.setCapacity(hostel.getCapacity());
		hostelDto.setContactNo(hostel.getContactNo());
		hostelDto.setImage(hostel.getImage());
		hostelDto.setName(hostel.getName());
		hostelDto.setType(hostel.getType());
		hostelDto.setWebsite(hostel.getWebsite());
		
		
		return hostelDto;
		
	}

	@Override
	public List<HostelDto> getAllHostels() {
		List<Hostel> hostels = hostelRepository.findAll();
		if(hostels.isEmpty()) {
			throw new HostelServiceException(ErrorConstant.HOSTEL_LIST_EMPTY, HttpStatus.NO_CONTENT);
		}
		
		List<HostelDto> hostelDtos = new ArrayList<>();
		
		for(Hostel hostel:hostels) {
			HostelDto hostelDto = new HostelDto();
			hostelDto.setAddress(hostel.getAddress());
			hostelDto.setCapacity(hostel.getCapacity());
			hostelDto.setContactNo(hostel.getContactNo());
			hostelDto.setImage(hostel.getImage());
			hostelDto.setName(hostel.getName());
			hostelDto.setType(hostel.getType());
			hostelDto.setWebsite(hostel.getWebsite());
			
			hostelDtos.add(hostelDto);
			
		}
		return hostelDtos;
	}

}
