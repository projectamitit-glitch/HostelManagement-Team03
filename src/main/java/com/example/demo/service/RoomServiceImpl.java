package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Floor;
import com.example.demo.Entity.Room;
import com.example.demo.constant.ErrorConstant;
import com.example.demo.dto.RoomDto;
import com.example.demo.exception.RoomServiceException;
import com.example.demo.repository.BedRepository;
import com.example.demo.repository.FloorRepository;
import com.example.demo.repository.RoomRepository;

@Service
public class RoomServiceImpl implements RoomService {
	
	public static final Logger logger = LoggerFactory.getLogger(RoomServiceImpl.class);

	@Autowired
	RoomRepository roomRepository;

	@Autowired
	FloorRepository floorRepository;

	@Autowired
	BedRepository bedRepository;

	@Override
	public void addRoom(RoomDto roomDto, int floorId) {

	    logger.info("Request received to add room to Floor ID: {}", floorId);
	    logger.debug("Incoming RoomDto: {}", roomDto);

	    Optional<Floor> o = floorRepository.findById(floorId);
	    logger.debug("Floor lookup result for ID {}: {}", floorId, o);

	    if (!o.isPresent()) {
	        logger.error("Floor not found for ID: {}", floorId);
	        throw new RoomServiceException("Floor not found", HttpStatus.BAD_REQUEST);
	    }

	    Floor floor = o.get();
	    logger.debug("Floor retrieved from DB: {}", floor);

	    Room room = new Room();

	    logger.debug("Setting room properties...");
	    room.setRoomNo(roomDto.getRoomNo());
	    room.setSharing(roomDto.getSharing());
	    room.setType(roomDto.getType());

	    room.setFloor(floor);

	    logger.info("Saving room into database...");
	    try {
	        roomRepository.save(room);
	    } catch (Exception e) {
	        logger.error("Error while saving room: {}", e.getMessage());
	        throw new RoomServiceException(ErrorConstant.ROOM_SAVE_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    logger.info("Room saved successfully. Updating room count for Floor ID: {}", floorId);
	    floor.setRoomCount(floor.getRoomCount() + 1);
	    floorRepository.save(floor);

	    logger.info("Room count updated successfully for Floor ID: {}", floorId);
	}


	@Override
	public List<RoomDto> getAllRooms() {

	    logger.info("Request received to fetch all rooms");

	    List<Room> rooms = roomRepository.findAll();
	    logger.debug("Fetched rooms from DB: {}", rooms);

	    if (rooms.isEmpty()) {
	        logger.error("No rooms found in the database");
	        throw new RoomServiceException(ErrorConstant.ROOMS_NOT_FOUND, HttpStatus.NOT_FOUND);
	    }

	    List<RoomDto> roomDtoList = new ArrayList<>();
	    logger.debug("Converting Room entities to RoomDto list");

	    for (Room room : rooms) {
	        RoomDto roomDto = new RoomDto();
	        roomDto.setRoomNo(room.getRoomNo());
	        roomDto.setSharing(room.getSharing());
	        roomDto.setType(room.getType());

	        roomDtoList.add(roomDto);
	    }

	    logger.info("Successfully fetched {} rooms", roomDtoList.size());
	    return roomDtoList;
	}


	@Override
	public RoomDto getRoom(int id) {

	    logger.info("Request received to get room by ID: {}", id);

	    Optional<Room> o = roomRepository.findById(id);
	    logger.debug("Room lookup result for ID {}: {}", id, o);

	    if (!o.isPresent()) {
	        logger.error("Room not found for ID: {}", id);
	        throw new RoomServiceException(ErrorConstant.ROOM_NOT_FOUND, HttpStatus.NOT_FOUND);
	    }

	    Room room = o.get();
	    logger.debug("Room retrieved from DB: {}", room);

	    RoomDto roomDto = new RoomDto();
	    roomDto.setRoomNo(room.getRoomNo());
	    roomDto.setSharing(room.getSharing());
	    roomDto.setType(room.getType());

	    logger.info("Room DTO prepared successfully for ID: {}", id);
	    return roomDto;
	}


	@Override
	public void deleteRoom(int id) {

	    logger.info("Request received to delete room with ID: {}", id);

	    Optional<Room> o = roomRepository.findById(id);
	    logger.debug("Room lookup result for ID {}: {}", id, o);

	    if (!o.isPresent()) {
	        logger.error("Room not found for ID: {}", id);
	        throw new RoomServiceException(ErrorConstant.ROOM_NOT_FOUND, HttpStatus.NOT_FOUND);
	    }

	    Room room = o.get();
	    logger.debug("Room retrieved from DB for deletion: {}", room);

	    Floor floor = room.getFloor();
	    logger.debug("Associated floor for room {}: {}", id, floor);

	    if (floor == null) {
	        logger.error("Floor not found for room ID: {}", id);
	        throw new RoomServiceException("Floor not found", HttpStatus.NOT_FOUND);
	    }

	    logger.info("Deleting room with ID: {}", id);
	    roomRepository.delete(room);

	    logger.info("Updating room count for Floor ID: {}", floor.getId());
	    floor.setRoomCount(floor.getRoomCount() - 1);
	    floorRepository.save(floor);

	    logger.info("Room deleted and floor count updated successfully for Floor ID: {}", floor.getId());
	}


	@Override
	public void deleteAll() {

	    logger.info("Request received to delete all rooms");

	    List<Room> rooms = roomRepository.findAll();
	    logger.debug("Fetched rooms from DB for deletion: {}", rooms);

	    if (rooms.isEmpty()) {
	        logger.error("No rooms found to delete");
	        throw new RoomServiceException(ErrorConstant.ROOMS_NOT_FOUND, HttpStatus.NOT_FOUND);
	    }

	    for (Room room : rooms) {
	        Floor floor = room.getFloor();
	        logger.debug("Processing room {} with associated floor: {}", room.getId(), floor);

	        if (floor == null) {
	            logger.error("Floor not found for room ID: {}", room.getId());
	            throw new RoomServiceException("floor not found", HttpStatus.NOT_FOUND);
	        }

	        logger.info("Decreasing room count for Floor ID: {}", floor.getId());
	        floor.setRoomCount(floor.getRoomCount() - 1);
	        floorRepository.save(floor);
	    }

	    logger.info("Deleting all rooms from database");
	    roomRepository.deleteAll(rooms);

	    logger.info("All rooms deleted successfully");
	}

}
