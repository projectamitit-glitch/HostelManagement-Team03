package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.BuildingDto;

public interface BuildingService {

	public void saveBuilding(BuildingDto buildingDto, int hostelId);

	List<BuildingDto> getAllBuildings();

	BuildingDto getBuildingById(int id);

	void deleteBuildingById(int id);

	void deleteAllBuildings();

}
