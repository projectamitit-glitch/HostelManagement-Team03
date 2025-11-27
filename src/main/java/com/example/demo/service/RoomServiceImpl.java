package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Floor;
import com.example.demo.Entity.Room;
import com.example.demo.dto.RoomDto;
import com.example.demo.exception.RoomServiceException;
import com.example.demo.repository.FloorRepository;
import com.example.demo.repository.RoomRepository;

@Service
public class RoomServiceImpl implements RoomService {

	@Autowired
	RoomRepository roomRepository;

	@Autowired
	FloorRepository floorRepository;

	@Override
	public void addRoom(RoomDto roomDto, int floorId) {

		Floor floor = floorRepository.findById(floorId).get();

		if (floor == null) {
			throw new RoomServiceException("floor not found", HttpStatus.BAD_REQUEST);
		}

		Room room = new Room();

		room.setRoomNo(roomDto.getRoomNo());
		room.setSharing(roomDto.getSharing());
		room.setType(roomDto.getType());
		
		room.setFloor(floor);
		roomRepository.save(room);
	}

}
