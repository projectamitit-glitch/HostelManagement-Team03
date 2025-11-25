package com.example.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Entity.Building;
import com.example.demo.Entity.Hostel;
import com.example.demo.dto.BuildingDto;
import com.example.demo.repository.BuildingRepository;
import com.example.demo.repository.HostelRepository;
@Service
public class BuildingServiceImpl implements BuildingService{
		@Autowired
		BuildingRepository buildingRepository;
		
		@Autowired
		HostelRepository hostelRepository;
		
		@Override
		public void saveBuilding(BuildingDto buildingDto, int hostelId) {
			
			Building building=new Building();
			building.setName(buildingDto.getName());
			building.setNoOfFloors(buildingDto.getNoOfFloors());
			building.setWarden(buildingDto.getWarden());
		
			Hostel hostel = hostelRepository.findById(hostelId).get();
			building.setHostel(hostel);
			
		    buildingRepository.save(building);
	}

}
