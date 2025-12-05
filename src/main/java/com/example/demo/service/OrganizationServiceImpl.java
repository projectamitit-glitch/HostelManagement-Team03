package com.example.demo.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Bed;
import com.example.demo.Entity.Organization;
import com.example.demo.constant.ErrorConstant;
import com.example.demo.dto.BedDto;
import com.example.demo.dto.OrganizationDto;
import com.example.demo.exception.BedServiceException;
import com.example.demo.exception.OraganizationServiceException;
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
			throw new OraganizationServiceException(ErrorConstant.ORGANIZATION_SAVE_EXCEPTION,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public List<OrganizationDto> getAllOrganizations() {

		List<Organization> organizations = organizationRepository.findAll();
		if (organizations.isEmpty()) {
			throw new OraganizationServiceException(ErrorConstant.ORGANIZATION_LIST_EMPTY, HttpStatus.NO_CONTENT);

		}
		List<OrganizationDto> dtoList = new LinkedList<OrganizationDto>();
		for (Organization org : organizations) {
			OrganizationDto oraganizationdto = new OrganizationDto();

			oraganizationdto.setAddress(org.getAddress());
			oraganizationdto.setName(org.getName());
			oraganizationdto.setOwnerName(org.getOwnerName());
			oraganizationdto.setEmail(org.getEmail());
			oraganizationdto.setContactNo(org.getContactNo());

			dtoList.add(oraganizationdto);
		}

		return dtoList;

	}

	@Override
	public OrganizationDto getOrganizationById(int id) {
		Optional<Organization> optionalOraganization = organizationRepository.findById(id);

		if (optionalOraganization.isEmpty()) {
			throw new OraganizationServiceException(ErrorConstant.ORGANIZATION_NOT_FOUND_ID, HttpStatus.NOT_FOUND);
		}
		Organization organization = optionalOraganization.get();
		OrganizationDto oraganizationDto = new OrganizationDto();
		oraganizationDto.setName(organization.getName());
		oraganizationDto.setOwnerName(organization.getOwnerName());
		oraganizationDto.setEmail(organization.getEmail());
		oraganizationDto.setContactNo(organization.getContactNo());
		oraganizationDto.setAddress(organization.getAddress());

		return oraganizationDto;
	}

	@Override
	public void deleteAllOrganizations() {

		try {
			organizationRepository.deleteAll();
		} catch (Exception e) {
			throw new OraganizationServiceException(ErrorConstant.ORGANIZATION_LIST_EMPTY,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public void deleteOrganizationById(int id) {

		Organization organization = organizationRepository.findById(id).orElse(null);

		if (organization == null) {
			throw new OraganizationServiceException(ErrorConstant.ORGANIZATION_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
		try {
			organizationRepository.deleteById(id);
		} catch (Exception e) {
			throw new OraganizationServiceException(ErrorConstant.ORAGANIZATION_DELETE_EXCEPTION,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
