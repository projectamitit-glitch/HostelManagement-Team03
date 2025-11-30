package com.example.demo.service;

import java.util.List;
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
		try {
			Organization organization = new Organization();
			organization.setAddress(organizationDto.getAddress());
			organization.setContactNo(organizationDto.getContactNo());
			organization.setEmail(organizationDto.getEmail());
			organization.setName(organizationDto.getName());
			organization.setOwnerName(organizationDto.getOwnerName());

			organizationRepository.save(organization);
		} catch (Exception e) {
			throw new OrganizationServiceException("Error while saving organization: ", HttpStatus.CONFLICT);
		}
	}

	@Override
	public Organization getOrganizationById(int id) {
		return organizationRepository.findById(id).orElseThrow(
				() -> new OrganizationServiceException("Organization not found with ID: " + id, HttpStatus.NOT_FOUND));
	}

	@Override
	public List<Organization> getAllOrganizations() {
		try {
			return organizationRepository.findAll();
		} catch (Exception e) {
			throw new OrganizationServiceException("Error fetching organizations: ", HttpStatus.CONFLICT);
		}
	}

	@Override
	public void deleteOrganizationById(int id) {
		Organization organization = organizationRepository.findById(id).get();

		if (organization == null) {
			throw new OrganizationServiceException("Cannot delete. Organization not found with ID: " + id,
					HttpStatus.NOT_FOUND);
		}
		try {
			organizationRepository.delete(organization);

		} catch (Exception e) {
			throw new OrganizationServiceException("Error deleting organization: ", HttpStatus.CONFLICT);
		}
	}

	@Override
	public void deleteAllOrganizations() {
		try {
			organizationRepository.deleteAll();
		} catch (Exception e) {
			throw new OrganizationServiceException("Error deleting all organizations: ", HttpStatus.CONFLICT);
		}
	}
}