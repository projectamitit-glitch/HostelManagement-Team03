package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.example.demo.Entity.Organization;
import com.example.demo.dto.OrganizationDto;
import com.example.demo.exception.OrganizationServiceException;
import com.example.demo.repository.OrganizationRepository;

@Service
public class OrganizationServiceImpl implements OrganizationService {
	@Autowired
	OrganizationRepository organizationRepository;

	@Override
	public void addOrganization(OrganizationDto organizationDto) {

		Organization organization = new Organization();
		organization.setAddress(organizationDto.getAddress());
		organization.setContactNo(organizationDto.getContactNo());
		organization.setEmail(organizationDto.getEmail());
		organization.setName(organizationDto.getName());
		organization.setOwnerName(organizationDto.getOwnerName());
		try {

			organizationRepository.save(organization);
		} catch (Exception e) {
			throw new OrganizationServiceException("Error while saving organization: ", HttpStatus.CONFLICT);
		}
	}

	@Override
	public OrganizationDto getOrganizationById(int id) {
		Organization organization = organizationRepository.findById(id).orElse(null);

		if (organization == null) {
			throw new OrganizationServiceException("Organization not found with ID: " + id, HttpStatus.NOT_FOUND);
		}

		OrganizationDto dto = new OrganizationDto();
		dto.setName(organization.getName());
		dto.setOwnerName(organization.getOwnerName());
		dto.setEmail(organization.getEmail());
		dto.setContactNo(organization.getContactNo());
		dto.setAddress(organization.getAddress());
		return dto;
	}

	@Override
	public List<OrganizationDto> getAllOrganizations() {
		List<Organization> organizations = organizationRepository.findAll();

		if (organizations.isEmpty()) {
			throw new OrganizationServiceException("No organizations found", HttpStatus.NOT_FOUND);
		}

		return organizations.stream().map(org -> {
			OrganizationDto dto = new OrganizationDto();
			dto.setName(org.getName());
			dto.setOwnerName(org.getOwnerName());
			dto.setEmail(org.getEmail());
			dto.setContactNo(org.getContactNo());
			dto.setAddress(org.getAddress());
			return dto;
		}).collect(Collectors.toList());
	}

	@Override
	public void deleteOrganizationById(int id) {
		Organization organization = organizationRepository.findById(id).orElse(null);

		if (organization == null) {
			throw new OrganizationServiceException("Organization not found with ID: " + id, HttpStatus.NOT_FOUND);
		}

		organizationRepository.delete(organization);
	}

	@Override
	public void deleteAllOrganizations() {
		List<Organization> organizations = organizationRepository.findAll();

		if (organizations.isEmpty()) {
			throw new OrganizationServiceException("No organizations available to delete", HttpStatus.NOT_FOUND);
		}

		organizationRepository.deleteAll();
	}
}

