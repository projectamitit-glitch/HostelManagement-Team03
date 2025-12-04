package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Address;
import com.example.demo.Entity.Hostel;
import com.example.demo.Entity.Organization;
import com.example.demo.constant.ErrorConstant;
import com.example.demo.dto.HostelDto;
import com.example.demo.exception.AddressServiceExcpetion;
import com.example.demo.exception.HostelServiceException;
import com.example.demo.exception.OrganizationServiceException;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.HostelRepository;
import com.example.demo.repository.OrganizationRepository;

@Service
public class HostelServiceImpl implements HostelService {

	@Autowired
	HostelRepository hostelRepository;

	@Autowired
	OrganizationRepository organizationRepository;
	@Autowired
	AddressRepository addressRepository;

	@Override
	public void saveHostel(HostelDto hostelDto, int organizationId) {

		Organization organization = organizationRepository.findById(organizationId).get();

		if (organization == null) {
			throw new OrganizationServiceException(ErrorConstant.ORGANIZATION_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		Hostel hostel = new Hostel();
		Address address=new Address();

		hostel.setOrganization(organization);
		hostel.setName(hostelDto.getName());
		hostel.setCapacity(hostelDto.getCapacity());
		hostel.setContactNo(hostelDto.getContactNo());
		hostel.setImage(hostelDto.getImage());
		hostel.setWebsite(hostelDto.getWebsite());
		hostel.setType(hostelDto.getType());

		hostel.setAddress(hostelDto.getAddress());
		hostelDto.getAddress().setHostel(hostel);
		
		
		Address address1=hostelDto.getAddress();
		addressRepository.save(address);
		if(address1==null) {
		 throw new AddressServiceExcpetion(ErrorConstant.ADDRESS_NOT_ADDED_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		Hostel hostel1=hostelRepository.save(hostel);
		if(hostel1==null) {
			throw new HostelServiceException(ErrorConstant.HOSTEL_SAVE_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public void deleteHostel(int id) {
		Hostel hostel = hostelRepository.findById(id).get();
		if (hostel == null) {
			throw new HostelServiceException(ErrorConstant.HOSTEL_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		hostelRepository.delete(hostel);

	}

	@Override
	public void deleteAllHostels() {
		List<Hostel> hostels = hostelRepository.findAll();
		if (hostels.isEmpty()) {
			throw new HostelServiceException(ErrorConstant.HOSTEL_DELETE_ALL_EXCEPTION, HttpStatus.NO_CONTENT);
		}
		hostelRepository.deleteAll(hostels);

	}

	@Override
	public HostelDto getHostel(int id) {
		Hostel hostel = hostelRepository.findById(id).get();
		if (hostel == null) {
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
		if (hostels.isEmpty()) {
			throw new HostelServiceException(ErrorConstant.HOSTEL_LIST_EMPTY, HttpStatus.NO_CONTENT);
		}

		List<HostelDto> hostelDtos = new ArrayList<>();

		for (Hostel hostel : hostels) {
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
	
	public List<HostelDto> searchHostel(String city, String area){
		List<Integer> hostelId=new ArrayList<>();
		
		if(area !=null && city !=null) {
			hostelId= addressRepository.findHostelIdsByCityAndArea(city, area);
		}
		
		else if(area !=null && city==null) {
			hostelId= addressRepository.findHostelIdsByArea(area);
		}
		
		else if(area ==null && city !=null) {
			hostelId= addressRepository.findHostelIdsByCity(city);
		}
		
		List<Hostel> hostelList=hostelRepository.findAllById(hostelId);
		
		List<HostelDto> hostelDtoList=new ArrayList<>();
		for(Hostel hostel: hostelList) {
			HostelDto hostelDto=new HostelDto();
			hostelDto.setAddress(hostel.getAddress());
			hostelDto.setCapacity(hostel.getCapacity());
			hostelDto.setContactNo(hostel.getContactNo());
			hostelDto.setImage(hostel.getImage());
			hostelDto.setName(hostel.getName());
			hostelDto.setType(hostel.getType());
			
			hostelDtoList.add(hostelDto);
		}
		return hostelDtoList;
	}

}
