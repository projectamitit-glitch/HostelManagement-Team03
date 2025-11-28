package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.dto.FloorDto;
import com.example.demo.exception.FloorServiceException;
import com.example.demo.repository.BuildingRepository;
import com.example.demo.repository.FloorRepository;
import com.example.demo.Entity.Building;
import com.example.demo.Entity.Floor;
import com.example.demo.constant.Constant;
import com.example.demo.constant.ErrorConstant;

@Service
public class FloorServiceImpl implements FloorService {
	@Autowired
	FloorRepository floorRepository;

	@Autowired
	BuildingRepository buildingRepository;

	@Override
	public void saveFloor(FloorDto floorDto, int buildingId) {
		Building building = buildingRepository.findById(buildingId)
				.orElseThrow(() -> new RuntimeException(ErrorConstant.BUILDING_NOT_FOUND));
		Floor floor = new Floor();

		floor.setFloorNo(floorDto.getFloorno());
		floor.setRoomCount(0);
		floor.setBuilding(building);

		try {
			Floor floor2 = floorRepository.save(floor);
			building.setFloorCount(building.getFloorCount() + 1);
			buildingRepository.save(building);
		} catch (FloorServiceException floorServiceException) {
			throw new FloorServiceException(ErrorConstant.FLOOR_SAVE_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
