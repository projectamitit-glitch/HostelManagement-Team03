package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Bed;
import com.example.demo.dto.BreakupDto;
import com.example.demo.repository.BedRepository;

@Service
public class BreakupServiceImpl implements BreakupService {

	@Autowired
	BedRepository bedRepository;

	@Override
	public BreakupDto getByBedIdAndDuration(int id, int duration) {

		Optional<Bed> optional = bedRepository.findById(id);
		if (optional.isEmpty()) {
			throw new RuntimeException("bed not found with id:" + id);
		}
		Bed bed = optional.get();

		int price = bed.getPrice();
		int finalAmount = price * duration;

		int bedNo = bed.getBedNo();
		int roomNo = bed.getRoom().getRoomNo();
		int floorNo = bed.getRoom().getFloor().getFloorNo();
		int buildingNo = bed.getRoom().getFloor().getBuilding().getId();
		int hostelNo = bed.getRoom().getFloor().getBuilding().getHostel().getId();
		String hostelName = bed.getRoom().getFloor().getBuilding().getHostel().getName();
		return new BreakupDto(price, duration, finalAmount, bedNo, roomNo, floorNo, buildingNo, hostelNo, hostelName);
	}

}
