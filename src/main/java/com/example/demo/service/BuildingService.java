package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.BuildingDto;


public interface BuildingService {

	public void saveBuilding(BuildingDto buildingDto, int hostelId);

	  BuildingDto getBuildingById(int id);

	    List<BuildingDto> getAllBuildings();
		BuildingDto getBuildingById (Integer id);

	    void deleteBuildingById(int id);

	    void deleteAllBuildings();

}
