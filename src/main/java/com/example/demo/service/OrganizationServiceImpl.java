package com.example.demo.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Organization;
import com.example.demo.constant.ErrorConstant;
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
			throw new OrganizationServiceException(ErrorConstant.ORGANIZATION_SAVE_EXCEPTION,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public List<OrganizationDto> getAllOrganizations() {

		List<Organization> organizations = organizationRepository.findAll();
		if (organizations.isEmpty()) {
			throw new OrganizationServiceException(ErrorConstant.ORGANIZATION_LIST_EMPTY, HttpStatus.NO_CONTENT);

		}
		List<OrganizationDto> dtoList = new LinkedList<OrganizationDto>();
		for (Organization org : organizations) {
			OrganizationDto organizationdto = new OrganizationDto();

			organizationdto.setAddress(org.getAddress());
			organizationdto.setName(org.getName());
			organizationdto.setOwnerName(org.getOwnerName());
			organizationdto.setEmail(org.getEmail());
			organizationdto.setContactNo(org.getContactNo());

			dtoList.add(organizationdto);
		}

		return dtoList;

	}

	@Override
	public OrganizationDto getOrganizationById(int id) {
		Optional<Organization> optionalOrganization = organizationRepository.findById(id);

		if (optionalOrganization.isEmpty()) {
			throw new OrganizationServiceException(ErrorConstant.ORGANIZATION_NOT_FOUND_ID, HttpStatus.NOT_FOUND);
		}
		Organization organization = optionalOrganization.get();
		OrganizationDto organizationDto = new OrganizationDto();
		organizationDto.setName(organization.getName());
		organizationDto.setOwnerName(organization.getOwnerName());
		organizationDto.setEmail(organization.getEmail());
		organizationDto.setContactNo(organization.getContactNo());
		organizationDto.setAddress(organization.getAddress());

		return organizationDto;
	}

	@Override
	public void deleteAllOrganizations() {

		try {
			organizationRepository.deleteAll();
		} catch (Exception e) {
			throw new OrganizationServiceException(ErrorConstant.ORGANIZATION_LIST_EMPTY,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public void deleteOrganizationById(int id) {

		Organization organization = organizationRepository.findById(id).orElse(null);

		if (organization == null) {
			throw new OrganizationServiceException(ErrorConstant.ORGANIZATION_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
		try {
			organizationRepository.deleteById(id);
		} catch (Exception e) {
			throw new OrganizationServiceException(ErrorConstant.ORGANIZATION_DELETE_EXCEPTION,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
