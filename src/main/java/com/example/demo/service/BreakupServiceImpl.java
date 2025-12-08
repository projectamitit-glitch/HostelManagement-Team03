package com.example.demo.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Bed;
import com.example.demo.constant.ErrorConstant;
import com.example.demo.dto.BreakupDto;
import com.example.demo.exception.BreakupServiceException;
import com.example.demo.repository.BedRepository;

@Service
public class BreakupServiceImpl implements BreakupService {

	@Autowired
	BedRepository bedRepository;

	@Override
	public BreakupDto getBreakup(int bedId, LocalDate startDate, int duration) {
		
		Optional<Bed> optional = bedRepository.findById(bedId);

	    if (optional.isEmpty()) {
	        throw new BreakupServiceException(ErrorConstant.BED_NOT_FOUND, HttpStatus.NOT_FOUND);
	    }

	    Bed bed = optional.get();
		
		int price = bed.getPrice();
		int deposit = bed.getDeposit();
		int finalAmount = price * duration + deposit;
		LocalDate endDate = startDate.plusMonths(duration).minusDays(1);

		

		int bedNo = bed.getBedNo();
		int roomNo = bed.getRoom().getRoomNo();
		int floorNo = bed.getRoom().getFloor().getFloorNo();
		int buildingId = bed.getRoom().getFloor().getBuilding().getId();
		int hostelId = bed.getRoom().getFloor().getBuilding().getHostel().getId();
		String hostelName = bed.getRoom().getFloor().getBuilding().getHostel().getName();
		return new BreakupDto(price, duration, finalAmount, bedNo, roomNo, floorNo, buildingId, hostelId, hostelName,startDate,endDate);
	}

		
		
	

//	@Override
//	public BreakupDto getByBedIdAndDuration(int id, int duration) {
//

}
