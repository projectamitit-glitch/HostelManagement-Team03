package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Floor;
import com.example.demo.Entity.Room;
import com.example.demo.constant.ErrorConstant;
import com.example.demo.dto.RoomDto;
import com.example.demo.exception.BedServiceException;
import com.example.demo.exception.RoomServiceException;
import com.example.demo.repository.BedRepository;
import com.example.demo.repository.FloorRepository;
import com.example.demo.repository.RoomRepository;

@Service
public class RoomServiceImpl implements RoomService {

	@Autowired
	RoomRepository roomRepository;

	@Autowired
	FloorRepository floorRepository;

	@Autowired
	BedRepository bedRepository;

	@Override
	public void addRoom(RoomDto roomDto, int floorId) {

		Floor floor = floorRepository.findById(floorId).get();

		if (floor == null) {
			throw new RoomServiceException("Floor not found", HttpStatus.BAD_REQUEST);
		}

		Room room = new Room();

		room.setRoomNo(roomDto.getRoomNo());
		room.setSharing(roomDto.getSharing());
		room.setType(roomDto.getType());

		room.setFloor(floor);

		Room room2 = roomRepository.save(room);
		if (room2 == null) {
			throw new RoomServiceException(ErrorConstant.ROOM_SAVE_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		floor.setRoomCount(floor.getRoomCount() + 1);
		floorRepository.save(floor);

	}

	@Override
	public List<RoomDto> getAllRooms() {

		List<Room> rooms = roomRepository.findAll();

		if (rooms.isEmpty()) {
			throw new RoomServiceException(ErrorConstant.ROOMS_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		List<RoomDto> roomDtoList = new ArrayList<>();

		for (Room room : rooms) {
			RoomDto roomDto = new RoomDto();
			roomDto.setRoomNo(room.getRoomNo());
			roomDto.setSharing(room.getSharing());
			roomDto.setType(room.getType());

			roomDtoList.add(roomDto);
		}
		return roomDtoList;
	}

	@Override
	public RoomDto getRoom(int id) {
		Room room = roomRepository.findById(id).get();

		if (room == null) {
			throw new RoomServiceException(ErrorConstant.ROOM_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		RoomDto roomDto = new RoomDto();
		roomDto.setRoomNo(room.getRoomNo());
		roomDto.setSharing(room.getSharing());
		roomDto.setType(room.getType());

		return roomDto;
	}

	@Override
	public void deleteRoom(int id) {
		Room room = roomRepository.findById(id).get();
		if (room == null) {
			throw new RoomServiceException(ErrorConstant.ROOM_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		Floor floor = room.getFloor();
		if (floor == null) {
			throw new RoomServiceException("Floor not found", HttpStatus.NOT_FOUND);
		}

		roomRepository.delete(room);

		floor.setRoomCount(floor.getRoomCount() - 1);
		floorRepository.save(floor);

	}

	@Override
	public void deleteAll() {

		List<Room> rooms = roomRepository.findAll();

		if (rooms.isEmpty()) {
			throw new RoomServiceException(ErrorConstant.ROOMS_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
		for (Room room : rooms) {
			Floor floor = room.getFloor();

			if (floor == null) {
				throw new RoomServiceException("floor  not found", HttpStatus.NOT_FOUND);
			}
			floor.setRoomCount(floor.getRoomCount() - 1);
			floorRepository.save(floor);

		}
		roomRepository.deleteAll(rooms);

	}

}
