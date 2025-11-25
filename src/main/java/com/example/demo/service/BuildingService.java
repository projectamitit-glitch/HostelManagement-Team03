package com.example.demo.service;

import com.example.demo.dto.BuildingDto;
import com.example.demo.dto.HostelDto;

public interface BuildingService {
	
	public void saveBuilding(BuildingDto buildingDto, int hostelId);


}
