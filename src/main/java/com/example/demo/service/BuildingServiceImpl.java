package com.example.demo.service;

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
}
