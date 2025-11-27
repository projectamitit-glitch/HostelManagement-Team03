package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Bed;
import com.example.demo.Entity.Room;
import com.example.demo.dto.BedDto;
import com.example.demo.exception.BedServiceException;
import com.example.demo.repository.BedRepository;
import com.example.demo.repository.RoomRepository;

@Service
public class BedServiceImpl implements BedService {

	@Autowired
	private BedRepository bedRepository;

	@Autowired
	private RoomRepository roomRepository;

	@Override
	public void addBed(BedDto bedDto) {
		Room room = roomRepository.findById(bedDto.getRoomId()).orElse(null);
		if (room == null) {
			throw new BedServiceException("Room not found with id: " + bedDto.getRoomId(), HttpStatus.NOT_FOUND);
		}

		Bed bed = new Bed();
		bed.setBedNo(bedDto.getBedNo());
		bed.setPrice(bedDto.getPrice());
		bed.setStatus(bedDto.getStatus());
		bed.setRoom(room);

		try {
			bedRepository.save(bed);
		} catch (Exception e) {
			throw new BedServiceException("Failed to save bed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
