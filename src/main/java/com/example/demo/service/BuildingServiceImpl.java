package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	public static final Logger logger = LoggerFactory.getLogger(BuildingServiceImpl.class);

	@Autowired
	BuildingRepository buildingRepository;

	@Autowired
	HostelRepository hostelRepository;

	@Override
	public void saveBuilding(BuildingDto buildingDto, int hostelId) {

		logger.info("Request received to save building for Hostel ID: {}", hostelId);
		logger.debug("Incoming BuildingDto: {}", buildingDto);

		Optional<Hostel> o = hostelRepository.findById(hostelId);
		logger.debug("Hostel lookup result: {}", o);

		if (!o.isPresent()) {
			logger.error("Hostel not found for ID: {}", hostelId);
			throw new BuildingServiceException("Hostel not found", HttpStatus.NOT_FOUND);
		}

		Hostel hostel = o.get();
		Building building = new Building();

		logger.debug("Setting building properties…");
		building.setName(buildingDto.getName());
		building.setFloorCount(0);
		building.setWarden(buildingDto.getWarden());

		building.setHostel(hostel);
		logger.info("Saving building in database…");

		Building building2 = buildingRepository.save(building);

		if (building2 == null) {
			logger.error("Failed to save building. Repository returned null.");
			throw new BuildingServiceException(ErrorConstant.BUILDING_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		logger.info("Building saved successfully.");
	}

	@Override
	public List<BuildingDto> getAllBuildings() {

		logger.info("Request received to fetch all buildings");

		List<Building> buildings = buildingRepository.findAll();
		logger.debug("Fetched buildings from DB: {}", buildings);

		if (buildings.isEmpty()) {
			logger.error("No buildings found in database");
			throw new BuildingServiceException(ErrorConstant.BUILDING_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		List<BuildingDto> dtos = new ArrayList<>();
		logger.debug("Converting Building entities to BuildingDto...");

		for (Building b : buildings) {
			BuildingDto dto = new BuildingDto();
			dto.setName(b.getName());
			dto.setFloorCount(b.getFloorCount());
			dto.setWarden(b.getWarden());
			dtos.add(dto);
		}

		logger.info("Successfully fetched {} buildings", dtos.size());
		return dtos;
	}

	@Override
	public BuildingDto getBuildingById(int id) {

		logger.info("Request received to get building by ID: {}", id);

		Optional<Building> o = buildingRepository.findById(id);
		logger.debug("Building lookup result for ID {}: {}", id, o);

		if (!o.isPresent()) {
			logger.error("Building not found for ID: {}", id);
			throw new BuildingServiceException(ErrorConstant.BUILDING_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		Building building = o.get();
		logger.debug("Building retrieved from DB: {}", building);

		BuildingDto dto = new BuildingDto();
		dto.setName(building.getName());
		dto.setFloorCount(building.getFloorCount());
		dto.setWarden(building.getWarden());

		logger.info("Building DTO successfully prepared for ID: {}", id);

		return dto;
	}

	@Override
	public void deleteAllBuildings() {

		logger.info("Request received to delete all buildings");

		List<Building> buildings = buildingRepository.findAll();
		logger.debug("Fetched buildings for deletion: {}", buildings);

		if (buildings.isEmpty()) {
			logger.error("No buildings found to delete");
			throw new BuildingServiceException(ErrorConstant.BUILDING_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		logger.info("Deleting {} buildings", buildings.size());
		buildingRepository.deleteAll(buildings);
	}

	@Override
	public void deleteBuildingById(int id) {

	    logger.info("Request received to delete building by ID: {}", id);

	    Optional<Building> o = buildingRepository.findById(id);
	    logger.debug("Building lookup result for ID {}: {}", id, o);

	    if (!o.isPresent()) {
	        logger.error("Building not found for ID: {}", id);
	        throw new BuildingServiceException(ErrorConstant.BUILDING_NOT_FOUND, HttpStatus.NOT_FOUND);
	    }

	    Building building = o.get();
	    logger.info("Deleting building with ID: {}", id);
	    buildingRepository.delete(building);
	}
}