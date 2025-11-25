package com.example.demo.service;

import com.example.demo.Entity.Building;
import com.example.demo.repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private BuildingRepository buildingRepository;

    @Override
    public Building addBuilding(Building building) {
        return buildingRepository.save(building);
    }

    @Override
    public List<Building> getAllBuildings() {
        return buildingRepository.findAll();
    }

    @Override
    public Building getBuildingById(int id) {
        return buildingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Building not found with ID: " + id));
    }

    @Override
    public Building getBuildingByName(String name) {
        Building building = buildingRepository.findByName(name);
        if (building == null) {
            throw new RuntimeException("Building not found with name: " + name);
        }
        return building;
    }

    @Override
    public List<Building> addAllBuildings(List<Building> buildings) {
        return buildingRepository.saveAll(buildings);
    }

    @Override
    public String getWardenByBuildingName(String name) {

        Building building = buildingRepository.findByName(name); // FIXED — removed casting

        if (building == null) {
            throw new RuntimeException("Building not found with name: " + name);
        }

        return building.getWarden();
    }

    @Override
    public String deleteBuilding(int id) {
        if (!buildingRepository.existsById(id)) {
            throw new RuntimeException("Building not found with ID: " + id);
        }
        buildingRepository.deleteById(id);
        return "Building deleted successfully with ID: " + id;
    }
}
