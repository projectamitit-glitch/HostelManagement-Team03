package com.example.demo.service;

import com.example.demo.Entity.Building;
import java.util.List;

public interface BuildingService {

	Building addBuilding(Building building);

	List<Building> getAllBuildings();

	Building getBuildingById(int id);

	Building getBuildingByName(String name);

	List<Building> addAllBuildings(List<Building> buildings);

	String getWardenByBuildingName(String name);

	String deleteBuilding(int id);
}
