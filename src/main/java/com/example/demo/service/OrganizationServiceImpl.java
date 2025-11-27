package com.example.demo.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
            throw new OrganizationServiceException("Error while saving organization");
        }
    }
    @Override
    public Organization getOrganizationById(int id) {
        try {
            Organization organization = organizationRepository.findById(id).get();
            return organization;
        } catch (Exception e) {
            throw new OrganizationServiceException("Organization not found with ID: " + id);
        }
    }
    @Override
    public List<Organization> getAllOrganizations() {
        try {
            return organizationRepository.findAll();
        } catch (Exception e) {
            throw new OrganizationServiceException("Error while fetching all organizations");
        }
    }
    @Override
    public void deleteOrganizationById(int id) {
        try {
            Organization organization = organizationRepository.findById(id).get();
            organizationRepository.deleteById(id);
        } catch (Exception e) {
            throw new OrganizationServiceException("Cannot delete. Organization not found with ID: " + id);
        }
    }
    @Override
    public void deleteAllOrganizations() {
        try {
            organizationRepository.deleteAll();
        } catch (Exception e) {
            throw new OrganizationServiceException("Error while deleting all organizations");
        }
    }
}
