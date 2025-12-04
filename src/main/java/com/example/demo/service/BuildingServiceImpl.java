package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

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
				.orElseThrow(() -> new BuildingServiceException(ErrorConstant.HOSTEL_NOT_FOUND, HttpStatus.NOT_FOUND));

		Building building = new Building();

		building.setName(buildingDto.getName());
		building.setFloorCount(0);
		building.setWarden(buildingDto.getWarden());

		building.setHostel(hostel);
		try {
			buildingRepository.save(building);
		}

		catch (BuildingServiceException buildingServiceException) {
			throw new BuildingServiceException(ErrorConstant.BUILDING_SAVE_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@Override
	public List<BuildingDto> getAllBuildings() {
		List<Building> buildings = buildingRepository.findAll();

		if (buildings.isEmpty()) {
			throw new BuildingServiceException(ErrorConstant.BUILDING_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		List<BuildingDto> dtos = new java.util.LinkedList<BuildingDto>(); // ArrayList avoid karne ke liye LinkedList
		for (Building b : buildings) {
			BuildingDto dto = new BuildingDto();
			dto.setName(b.getName());
			dto.setFloorCount(b.getFloorCount());
			dto.setWarden(b.getWarden());
			dtos.add(dto);
		}

		return dtos;
	}

	@Override
	public BuildingDto getBuildingById(int id) {
		Building building = buildingRepository.findById(id).orElse(null);

		if (building == null) {
			throw new BuildingServiceException(ErrorConstant.BUILDING_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		BuildingDto dto = new BuildingDto();
		dto.setName(building.getName());
		dto.setFloorCount(building.getFloorCount());
		dto.setWarden(building.getWarden());
		return dto;
	}

	@Override
	public void deleteBuildingById(int id) {
		Building building = buildingRepository.findById(id).orElse(null);

		if (building == null) {
			throw new BuildingServiceException(ErrorConstant.BUILDING_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		buildingRepository.delete(building);
	}

	@Override
	public void deleteAllBuildings() {
		List<Building> buildings = buildingRepository.findAll();

		if (buildings.isEmpty()) {
			throw new BuildingServiceException(ErrorConstant.BUILDING_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		buildingRepository.deleteAll(buildings);
	}

}
