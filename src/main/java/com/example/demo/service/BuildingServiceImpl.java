package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Building;
import com.example.demo.Entity.Hostel;
import com.example.demo.dto.BuildingDto;
import com.example.demo.exception.BuildingServiceException;
import com.example.demo.repository.BuildingRepository;
import com.example.demo.repository.HostelRepository;

@Service
public class BuildingServiceImpl implements BuildingService {
	@Autowired
	BuildingRepository buildingRepository;

	@Autowired
	HostelRepository hostelRepository;

	@Override
	public void saveBuilding(BuildingDto buildingDto, int hostelId) {

		Hostel hostel = hostelRepository.findById(hostelId)
				.orElseThrow(() -> new BuildingServiceException("hostel not found", HttpStatus.NOT_FOUND));

		Building building = new Building();

		building.setName(buildingDto.getName());
		building.setFloorCount(0);
		building.setWarden(buildingDto.getWarden());

		building.setHostel(hostel);

		try {
			buildingRepository.save(building);
		}

		catch (BuildingServiceException buildingServiceException) {
			throw new BuildingServiceException("Error occurred while saving building: ",
					HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@Override
	public Building getBuildingById(int id) {
		try {
			Building building = buildingRepository.findById(id).get();
			return building;
		} catch (Exception e) {
			throw new BuildingServiceException("building not found with ID: " + id, null);
		}
	}

	@Override
	public List<Building> getAllBuildings() {
		try {
			return buildingRepository.findAll();
		} catch (Exception e) {
			throw new BuildingServiceException("Error while fetching all building", null);
		}
	}

	@Override
	public void deleteBuildingById(int id) {
		try {
			Building building = buildingRepository.findById(id).get();
			buildingRepository.deleteById(id);
		} catch (Exception e) {
			throw new BuildingServiceException("Cannot delete. Organization not found with ID: " + id, null);
		}
	}

	@Override
	public void deleteAllBuildings() {
		try {
			buildingRepository.deleteAll();
		} catch (Exception e) {
			throw new BuildingServiceException("Error while deleting all organizations", null);
		}
	}

}
