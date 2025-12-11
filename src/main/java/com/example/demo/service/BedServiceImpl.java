
package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Bed;
import com.example.demo.Entity.Building;
import com.example.demo.Entity.Floor;
import com.example.demo.Entity.Hostel;
import com.example.demo.Entity.Room;
import com.example.demo.constant.ErrorConstant;
import com.example.demo.dto.BedDto;
import com.example.demo.exception.BedServiceException;
import com.example.demo.repository.BedRepository;
import com.example.demo.repository.RoomRepository;

@Service
public class BedServiceImpl implements BedService {
	public static final Logger logger = LoggerFactory.getLogger(BedServiceImpl.class);

	@Autowired
	BedRepository bedRepository;

	@Autowired
	RoomRepository roomRepository;

	@Override
	public void addBed(int roomId, BedDto bedDto) {

		logger.info("Request received to add bed to Room ID: {}", roomId);
		logger.debug("Incoming BedDto: {}", bedDto);

		Optional<Room> o = roomRepository.findById(roomId);
		logger.debug("Room lookup result for ID {}: {}", roomId, o);

		if (!o.isPresent()) {
			logger.error("Room not found for ID: {}", roomId);
			throw new BedServiceException(ErrorConstant.ROOM_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		Room room = o.get();
		logger.debug("Room retrieved from DB: {}", room);

		Bed bed = new Bed();
		logger.debug("Setting bed properties...");
		bed.setBedNo(bedDto.getBedNo());
		bed.setPrice(bedDto.getPrice());
		bed.setStatus(bedDto.getStatus());
		bed.setDeposit(bedDto.getDeposit());
		bed.setRoom(room);

		try {
			logger.info("Saving bed into database...");
			bedRepository.save(bed);

			logger.info("Updating sharing count for Room ID: {}", roomId);
			room.setSharing(room.getSharing() + 1);
			roomRepository.save(room);

			logger.info("Bed added successfully for Room ID: {}", roomId);

		} catch (Exception e) {
			logger.error("Error while saving bed: {}", e.getMessage());
			throw new BedServiceException(ErrorConstant.BED_SAVE_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public List<BedDto> getAllBeds() {

		logger.info("Request received to fetch all beds");

		List<Bed> beds = bedRepository.findAll();
		logger.debug("Fetched beds from DB: {}", beds);

		if (beds.isEmpty()) {
			logger.error("No beds found in the database");
			throw new BedServiceException(ErrorConstant.BED_LIST_EMPTY, HttpStatus.NO_CONTENT);
		}

		List<BedDto> bedDtos = new ArrayList<>();
		logger.debug("Converting Bed entities to BedDto list");

		for (Bed bed : beds) {
			BedDto bedDto = new BedDto();

			bedDto.setBedNo(bed.getBedNo());
			bedDto.setStatus(bed.getStatus());
			bedDto.setPrice(bed.getPrice());
			bedDto.setDeposit(bed.getDeposit());

			bedDtos.add(bedDto);
		}

		logger.info("Successfully fetched {} beds", bedDtos.size());
		return bedDtos;
	}

	@Override
	public BedDto getBedById(int id) {

		logger.info("Request received to get bed by ID: {}", id);

		Optional<Bed> optionalBed = bedRepository.findById(id);
		logger.debug("Bed lookup result for ID {}: {}", id, optionalBed);

		if (optionalBed.isEmpty()) {
			logger.error("Bed not found for ID: {}", id);
			throw new BedServiceException(ErrorConstant.BED_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		Bed bed = optionalBed.get();
		logger.debug("Bed retrieved from DB: {}", bed);

		BedDto bedDto = new BedDto();
		bedDto.setBedNo(bed.getBedNo());
		bedDto.setPrice(bed.getPrice());
		bedDto.setStatus(bed.getStatus());
		bedDto.setDeposit(bed.getDeposit());

		logger.info("Bed details returned successfully for ID: {}", id);
		return bedDto;
	}

	@Override
	public void deleteAllBeds() {

		logger.info("Request received to delete all beds");

		try {
			logger.info("Deleting all beds from database");
			bedRepository.deleteAll();

			logger.debug("Fetching all rooms to reset sharing count");
			List<Room> rooms = roomRepository.findAll();
			logger.debug("Rooms fetched for sharing reset: {}", rooms);

			for (Room room : rooms) {
				logger.debug("Resetting sharing count for Room ID: {}", room.getId());
				room.setSharing(0);
			}

			logger.info("Saving all rooms after resetting sharing count");
			roomRepository.saveAll(rooms);

			logger.info("All beds deleted and sharing count reset successfully");

		} catch (Exception e) {
			logger.error("Error while deleting all beds or updating rooms: {}", e.getMessage());
			throw new BedServiceException(ErrorConstant.BED_DELETE_ALL_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public void deleteBedById(int id) {

		logger.info("Request received to delete bed with ID: {}", id);

		Optional<Bed> optionalBed = bedRepository.findById(id);
		logger.debug("Bed lookup result for ID {}: {}", id, optionalBed);

		if (optionalBed.isEmpty()) {
			logger.error("Bed not found for ID: {}", id);
			throw new BedServiceException(ErrorConstant.BED_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		Bed bed = optionalBed.get();
		logger.debug("Bed retrieved from DB for deletion: {}", bed);

		Room room = bed.getRoom();
		logger.debug("Associated room for bed {}: {}", id, room);

		try {
			logger.info("Deleting bed with ID: {}", id);
			bedRepository.deleteById(id);

			int currentSharing = room.getSharing();
			logger.debug("Current sharing for Room ID {}: {}", room.getId(), currentSharing);

			if (currentSharing > 0) {
				room.setSharing(currentSharing - 1);
			} else {
				room.setSharing(0);
			}

			logger.info("Updating sharing count for Room ID: {}", room.getId());
			roomRepository.save(room);

			logger.info("Bed deleted and room sharing count updated successfully for Room ID: {}", room.getId());

		} catch (Exception e) {
			logger.error("Error deleting bed ID {}: {}", id, e.getMessage());
			throw new BedServiceException(ErrorConstant.BED_DELETE_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public List<BedDto> getAvailableBedsByRoomSharing(int hostelId, int sharing) {

		logger.info("Request received to get available beds for Hostel ID: {} with sharing: {}", hostelId, sharing);

		List<Bed> beds = bedRepository.findAvailableBedsByRoomSharing(sharing, hostelId);
		logger.debug("Available beds fetched from DB: {}", beds);

		if (beds.isEmpty()) {
			logger.error("No available beds found for Hostel ID: {} with sharing: {}", hostelId, sharing);
			throw new BedServiceException(ErrorConstant.AVAILABLE_BEDS_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		List<BedDto> bedDtos = new ArrayList<>();
		logger.debug("Preparing BedDto list for available beds");

		for (Bed bed : beds) {
			try {

				Room room = bed.getRoom();
				Floor floor = room.getFloor();
				Building building = floor.getBuilding();
				Hostel hostel = building.getHostel();

				logger.debug("Processing bed {} with room {}, floor {}, building {}, hostel {}", bed.getBedNo(),
						room.getRoomNo(), floor.getFloorNo(), building.getName(), hostel.getName());

				BedDto bedDto = new BedDto();

				bedDto.setBedNo(bed.getBedNo());
				bedDto.setStatus(bed.getStatus());
				bedDto.setPrice(bed.getPrice());
				bedDto.setDeposit(bed.getDeposit());

				bedDto.setRoomNo(room.getRoomNo());
				bedDto.setFloorNo(floor.getFloorNo());
				bedDto.setBuildingName(building.getName());
				bedDto.setHostelName(hostel.getName());

				bedDtos.add(bedDto);

			} catch (Exception e) {
				logger.error("Invalid bed data for bed ID {}: {}", bed.getId(), e.getMessage());
				throw new BedServiceException(ErrorConstant.INVALID_BED_DATA, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		logger.info("Successfully fetched {} available beds", bedDtos.size());
		return bedDtos;
	}

}