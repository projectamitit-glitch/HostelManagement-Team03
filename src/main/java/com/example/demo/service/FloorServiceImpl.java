package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	@Autowired
	FloorRepository floorRepository;

	@Autowired
	BuildingRepository buildingRepository;

	@Override
	public void saveFloor(FloorDto floorDto, int buildingId) {
		Optional<Building> optionalBuilding = buildingRepository.findById(buildingId);
		if (optionalBuilding.isEmpty()) {
			throw new BuildingServiceException(ErrorConstant.BUILDING_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
		Building building = optionalBuilding.get();
		Floor floor = new Floor();

		floor.setFloorNo(floorDto.getFloorNo());
		floor.setRoomCount(0);
		floor.setBuilding(building);

		try {
			 floorRepository.save(floor);
			building.setFloorCount(building.getFloorCount() + 1);
			buildingRepository.save(building);
		} catch (FloorServiceException floorServiceException) {
			throw new FloorServiceException(ErrorConstant.FLOOR_SAVE_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public FloorDto getFloor(int floorId) {

		Optional<Floor> optionalfloor = floorRepository.findById(floorId);
		if (optionalfloor.isEmpty()) {

			throw new FloorServiceException(ErrorConstant.FLOOR_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
		Floor floor = optionalfloor.get();
		FloorDto floorDto = new FloorDto();
		floorDto.setFloorNo(floor.getFloorNo());
		floorDto.setRoomCount(floor.getRoomCount());
		return floorDto;
	}

	@Override
	public List<FloorDto> getFloors() {
		// TODO Auto-generated method stub
		List<Floor> floors = floorRepository.findAll();
		if (floors.isEmpty()) {
			throw new FloorServiceException(ErrorConstant.FLOOR_NOT_FOUND, HttpStatus.NO_CONTENT);
		}
		List<FloorDto> floorDtos = new ArrayList();
		for (Floor floor : floors) {
			FloorDto floordto = new FloorDto();
			floordto.setFloorNo(floor.getFloorNo());
			floordto.setRoomCount(floor.getRoomCount());
			floorDtos.add(floordto);
		}

		return floorDtos;
	}

	@Override
	public void deleteFloor(int floorId) {
		Optional<Floor> optionalFloor = floorRepository.findById(floorId);
		if (optionalFloor.isEmpty()) {
			throw new FloorServiceException(ErrorConstant.FLOOR_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
		Floor floor = optionalFloor.get();
		Building building = floor.getBuilding();
		if (building == null) {
			throw new BuildingServiceException(ErrorConstant.BUILDING_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
		floorRepository.deleteById(floorId);

		building.setFloorCount(building.getFloorCount() - 1);
		buildingRepository.save(building);
	}

	@Override
	public void deleteFloors() {

		floorRepository.deleteAll();
		List<Building> buildings = buildingRepository.findAll();
		if (buildings == null) {
			throw new BuildingServiceException(ErrorConstant.BUILDING_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		for (Building building : buildings) {
			building.setFloorCount(0);
			buildingRepository.save(building);
		}

	}
}
