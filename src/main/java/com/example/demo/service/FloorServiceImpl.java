package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.dto.FloorDto;
import com.example.demo.exception.BuildingServiceException;
import com.example.demo.exception.FloorServiceException;
import com.example.demo.repository.BuildingRepository;
import com.example.demo.repository.FloorRepository;

import com.example.demo.Entity.Building;
import com.example.demo.Entity.Floor;
import com.example.demo.constant.ErrorConstant;

@Service
public class FloorServiceImpl implements FloorService {
	public static final Logger logger = LoggerFactory.getLogger(FloorServiceException.class);
	@Autowired
	FloorRepository floorRepository;

	@Autowired
	BuildingRepository buildingRepository;

	@Override
	public void saveFloor(FloorDto floorDto, int buildingId) {

		logger.info("Request received to save floor for Building ID: {}", buildingId);
		logger.debug("Incoming FloorDto: {}", floorDto);

		Optional<Building> optionalBuilding = buildingRepository.findById(buildingId);
		logger.debug("Building lookup result: {}", optionalBuilding);

		if (!optionalBuilding.isPresent()) {
			logger.error("Building not found for ID: {}", buildingId);
			throw new BuildingServiceException(ErrorConstant.BUILDING_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		Building building = optionalBuilding.get();
		Floor floor = new Floor();

		logger.debug("Setting floor properties...");
		floor.setFloorNo(floorDto.getFloorNo());
		floor.setRoomCount(0);
		floor.setBuilding(building);

		try {
			logger.info("Saving floor into database...");
			floorRepository.save(floor);

			logger.info("Updating floor count for Building ID: {}", buildingId);
			building.setFloorCount(building.getFloorCount() + 1);

			buildingRepository.save(building);
			logger.info("Floor saved successfully for Building ID: {}", buildingId);

		} catch (Exception exception) {
			logger.error("Error occurred while saving floor: {}", exception.getMessage());
			throw new FloorServiceException(ErrorConstant.FLOOR_SAVE_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public FloorDto getFloor(int floorId) {

		logger.info("Request received to get floor by ID: {}", floorId);

		Optional<Floor> optionalfloor = floorRepository.findById(floorId);
		logger.debug("Floor lookup result for ID {}: {}", floorId, optionalfloor);

		if (optionalfloor.isEmpty()) {
			logger.error("Floor not found for ID: {}", floorId);
			throw new FloorServiceException(ErrorConstant.FLOOR_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		Floor floor = optionalfloor.get();
		logger.debug("Floor retrieved from DB: {}", floor);

		FloorDto floorDto = new FloorDto();
		floorDto.setFloorNo(floor.getFloorNo());
		floorDto.setRoomCount(floor.getRoomCount());

		logger.info("Floor DTO prepared successfully for ID: {}", floorId);
		return floorDto;
	}

	@Override
	public List<FloorDto> getFloors() {

		logger.info("Request received to fetch all floors");

		List<Floor> floors = floorRepository.findAll();
		logger.debug("Fetched floors from DB: {}", floors);

		if (floors.isEmpty()) {
			logger.error("No floors found in database");
			throw new FloorServiceException(ErrorConstant.FLOOR_NOT_FOUND, HttpStatus.NO_CONTENT);
		}

		List<FloorDto> floorDtos = new ArrayList<>();
		logger.debug("Converting Floor entities to FloorDto list");

		for (Floor floor : floors) {
			FloorDto floordto = new FloorDto();
			floordto.setFloorNo(floor.getFloorNo());
			floordto.setRoomCount(floor.getRoomCount());
			floorDtos.add(floordto);
		}

		logger.info("Successfully fetched {} floors", floorDtos.size());
		return floorDtos;
	}

	@Override
	public void deleteFloor(int floorId) {

		logger.info("Request received to delete floor with ID: {}", floorId);

		Optional<Floor> optionalFloor = floorRepository.findById(floorId);
		logger.debug("Floor lookup result for ID {}: {}", floorId, optionalFloor);

		if (!optionalFloor.isPresent()) {
			logger.error("Floor not found for ID: {}", floorId);
			throw new FloorServiceException(ErrorConstant.FLOOR_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		Floor floor = optionalFloor.get();
		logger.debug("Floor retrieved from DB: {}", floor);

		Building building = floor.getBuilding();
		logger.debug("Associated building for floor {}: {}", floorId, building);

		if (building == null) {
			logger.error("Building not found for floor ID: {}", floorId);
			throw new BuildingServiceException(ErrorConstant.BUILDING_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		logger.info("Deleting floor with ID: {}", floorId);
		floorRepository.deleteById(floorId);

		logger.info("Updating floor count for building ID: {}", building.getId());
		building.setFloorCount(building.getFloorCount() - 1);

		try {
			buildingRepository.save(building);
			logger.info("Building updated successfully after deleting floor {}", floorId);
		} catch (Exception e) {
			logger.error("Error updating building after floor deletion: {}", e.getMessage());
			throw new BuildingServiceException(ErrorConstant.BUILDING_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public void deleteFloors() {

		logger.info("Request received to delete all floors");

		floorRepository.deleteAll();
		logger.info("All floors have been deleted");

		List<Building> buildings = buildingRepository.findAll();
		logger.debug("Fetched buildings to reset floor count: {}", buildings);

		if (buildings == null) {
			logger.error("No buildings found while resetting floor count");
			throw new BuildingServiceException(ErrorConstant.BUILDING_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		for (Building building : buildings) {
			logger.debug("Resetting floor count for Building ID: {}", building.getId());
			building.setFloorCount(0);

			try {
				buildingRepository.save(building);
				logger.info("Floor count reset successfully for Building ID: {}", building.getId());
			} catch (Exception e) {
				logger.error("Error while resetting floor count for Building ID {}: {}", building.getId(),
						e.getMessage());
				throw new BuildingServiceException(ErrorConstant.BUILDING_NOT_FOUND, HttpStatus.NOT_FOUND);
			}
		}
	}

}


