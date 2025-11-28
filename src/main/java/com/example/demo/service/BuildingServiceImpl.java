package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Building;
import com.example.demo.Entity.Hostel;
import com.example.demo.constant.ErrorConstant;
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
				.orElseThrow(() -> new BuildingServiceException(ErrorConstant.HOSTEL_NOT_FOUND , HttpStatus.NOT_FOUND));

		Building building = new Building();

		building.setName(buildingDto.getName());
		building.setFloorCount(0);
		building.setWarden(buildingDto.getWarden());

		building.setHostel(hostel);

		buildingRepository.save(building);

		throw new BuildingServiceException("Error occurred while saving building: ", HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@Override
	public BuildingDto getBuildingById(int id) {

		Building building = buildingRepository.findById(id).get();

		throw new BuildingServiceException("Cannot delete. Building not found with ID: " + id, HttpStatus.NOT_FOUND);

	}

	@Override
	public List<BuildingDto> getAllBuildings() {
		buildingRepository.findAll();

		throw new BuildingServiceException("Cannot delete. Building not found", HttpStatus.NOT_FOUND);
	}

	@Override
	public void deleteBuildingById(int id) {

		if (!buildingRepository.existsById(id)) {
			throw new BuildingServiceException("Building not found", HttpStatus.NOT_FOUND);
		}

		catch (BuildingServiceException buildingServiceException) {
			throw new BuildingServiceException(ErrorConstant.BUILDING_SAVE_EXCEPTION,
					HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@Override
	public void deleteAllBuildings() {

		if (buildingRepository.findAll().isEmpty()) {
			throw new BuildingServiceException("No buildings available to delete.", HttpStatus.NOT_FOUND);
		}

		buildingRepository.deleteAll();
	}

}
