package com.example.demo.service;

import java.util.List;



import com.example.demo.Entity.Building;
import com.example.demo.dto.BuildingDto;


public interface BuildingService {

	public void saveBuilding(BuildingDto buildingDto, int hostelId);

	Building getBuildingById(int id);

	List<Building> getAllBuildings();

	void deleteBuildingById(int id);

	void deleteAllBuildings();


}
