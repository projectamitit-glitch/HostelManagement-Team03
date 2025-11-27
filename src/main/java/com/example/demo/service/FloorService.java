package com.example.demo.service;

import com.example.demo.dto.FloorDto;

public interface FloorService {
	void saveFloor(FloorDto floorDto, int buildingId);
}
