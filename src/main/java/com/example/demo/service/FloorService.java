package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.FloorDto;

public interface FloorService {
	void saveFloor(FloorDto floorDto, int buildingId);

	FloorDto getFloor(int floorId);

	List<FloorDto> getFloors();

	void deleteFloors();

	void deleteFloor(int floorId);
}
