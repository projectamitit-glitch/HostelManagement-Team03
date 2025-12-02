package com.example.demo.service;

import java.util.List;

import com.example.demo.Entity.Building;
import com.example.demo.dto.BuildingDto;
import com.example.demo.dto.HostelDto;

public interface BuildingService {
	
	public void saveBuilding(BuildingDto buildingDto, int hostelId);

	List<BuildingDto> getAllBuildings();

		BuildingDto getBuildingById (Integer id);

	    void deleteBuildingById(Integer id);

	    void deleteAllBuildings();

}
