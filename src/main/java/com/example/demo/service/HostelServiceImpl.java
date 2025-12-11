package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Address;
import com.example.demo.Entity.Hostel;
import com.example.demo.Entity.Organization;
import com.example.demo.constant.ErrorConstant;
import com.example.demo.dto.HostelDto;
import com.example.demo.exception.AddressServiceException;
import com.example.demo.exception.HostelServiceException;
import com.example.demo.exception.OrganizationServiceException;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.HostelRepository;
import com.example.demo.repository.OrganizationRepository;

@Service
public class HostelServiceImpl implements HostelService {

	public static final Logger logger = LoggerFactory.getLogger(HostelServiceImpl.class);

	@Autowired
	HostelRepository hostelRepository;

	@Autowired
	OrganizationRepository organizationRepository;

	@Autowired
	AddressRepository addressRepository;

	@Override
	public void saveHostel(HostelDto hostelDto, int organizationId) {

		logger.info("Request received to save hostel for Organization ID: {}", organizationId);
		logger.debug("Incoming HostelDto details: {}", hostelDto);

		logger.trace("Fetching organization from database...");
		Optional<Organization> o = organizationRepository.findById(organizationId);

		if (!o.isPresent()) {
			logger.error("Organization not found for ID: {}", organizationId);
			throw new OrganizationServiceException(ErrorConstant.ORGANIZATION_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		logger.info("Organization found. Proceeding with hostel save process.");
		Organization organization = o.get();

		Hostel hostel = new Hostel();
		hostel.setOrganization(organization);
		hostel.setName(hostelDto.getName());
		hostel.setCapacity(hostelDto.getCapacity());
		hostel.setContactNo(hostelDto.getContactNo());
		hostel.setImage(hostelDto.getImage());
		hostel.setWebsite(hostelDto.getWebsite());
		hostel.setType(hostelDto.getType());

		logger.trace("Validating hostel address...");
		Address address = hostelDto.getAddress();
		if (address == null) {
			logger.error("Address is missing in HostelDto.");
			throw new AddressServiceException(ErrorConstant.ADDRESS_NOT_ADDED_EXCEPTION,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		hostel.setAddress(hostelDto.getAddress());
		hostelDto.getAddress().setHostel(hostel);

		logger.trace("Saving address to database...");
		try {
			addressRepository.save(address);
			logger.info("Address saved successfully.");
		} catch (Exception e) {
			logger.error("Error while saving address: {}", e.getMessage());
			throw new AddressServiceException(ErrorConstant.ADDRESS_SAVE_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.trace("Saving hostel to database...");
		try {
			hostelRepository.save(hostel);
			logger.info("Hostel saved successfully for Organization ID: {}", organizationId);
		} catch (Exception e) {
			logger.error("Error while saving hostel: {}", e.getMessage());
			throw new HostelServiceException(ErrorConstant.HOSTEL_SAVE_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public void deleteHostel(int id) {
		logger.info("Request received to delete hostel with ID: {}", id);

		Optional<Hostel> o = hostelRepository.findById(id);
		if (!o.isPresent()) {
			logger.error("Hostel not found");
			throw new HostelServiceException(ErrorConstant.HOSTEL_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
		logger.debug("Hostel found. Proceeding to delete. Hostel details: {}", o.get());

		hostelRepository.delete(o.get());
		logger.info("Hostel deleted successfully with ID: {}", id);

	}

	@Override
	public void deleteAllHostels() {

		logger.info("Request received to delete all hostels");
		List<Hostel> hostels = hostelRepository.findAll();

		if (hostels.isEmpty()) {

			logger.error("hostel list is empty:{}", ErrorConstant.HOSTEL_DELETE_ALL_EXCEPTION);
			throw new HostelServiceException(ErrorConstant.HOSTEL_DELETE_ALL_EXCEPTION, HttpStatus.NO_CONTENT);
		}

		logger.debug("Total hostels found: {}. Proceeding to delete.", hostels.size());
		hostelRepository.deleteAll(hostels);
		logger.info("All hostels deleted sucessfully.");

	}

	@Override
	public HostelDto getHostel(int id) {

		logger.info("Request received to fetch hostel with ID: {}", id);
		Optional<Hostel> o = hostelRepository.findById(id);
		logger.trace("Fetching hostel details from database...");
		if (!o.isPresent()) {
			logger.error("Hostel not found: {}", ErrorConstant.HOSTEL_NOT_FOUND);
			throw new HostelServiceException(ErrorConstant.HOSTEL_NOT_FOUND, HttpStatus.NOT_FOUND);

		}
		Hostel hostel = o.get();
		logger.debug("Hostel found. Preparing to convert into DTO. Hostel details: {}", hostel);

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

	public List<HostelDto> searchHostel(String city, String area) {

		logger.info("Request received to search hostels. City: {}, Area: {}", city, area);

		List<Integer> hostelId = new ArrayList<>();
		logger.trace("Checking search filters..."); // TRACE LOGGER

		if (area != null && city != null) {
			logger.debug("Searching by city and area. City: {}, Area: {}", city, area);
			hostelId = addressRepository.findHostelIdsByCityAndArea(city, area);
		}

		else if (area != null && city == null) {
			logger.debug("Searching by area only. Area: {}", area);
			hostelId = addressRepository.findHostelIdsByArea(area);
		}

		else if (area == null && city != null) {
			logger.debug("Searching by city only. City: {}", city);
			hostelId = addressRepository.findHostelIdsByCity(city);
		}

		logger.trace("Fetching hostel entities from database by ID list...");
		List<Hostel> hostelList = hostelRepository.findAllById(hostelId);

		List<HostelDto> hostelDtoList = new ArrayList<>();

		for (Hostel hostel : hostelList) {
			HostelDto hostelDto = new HostelDto();
			hostelDto.setAddress(hostel.getAddress());
			hostelDto.setCapacity(hostel.getCapacity());
			hostelDto.setContactNo(hostel.getContactNo());
			hostelDto.setImage(hostel.getImage());
			hostelDto.setName(hostel.getName());
			hostelDto.setType(hostel.getType());

			hostelDtoList.add(hostelDto);
		}

		logger.info("Search complete. Total hostels found: {}", hostelDtoList.size()); // INFO LOGGER
		return hostelDtoList;
	}

}
