
package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

	@Autowired
	BedRepository bedRepository;

	@Autowired
	RoomRepository roomRepository;

	@Override
	public void addBed(int roomId, BedDto bedDto) {
		Room room = roomRepository.findById(roomId).get();
		if (room == null) {
			throw new BedServiceException(ErrorConstant.ROOM_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		Bed bed = new Bed();
		bed.setBedNo(bedDto.getBedNo());
		bed.setPrice(bedDto.getPrice());
		bed.setStatus(bedDto.getStatus());
		bed.setDeposit(bedDto.getDeposit());
		bed.setRoom(room);

		try {
			bedRepository.save(bed);

			room.setSharing(room.getSharing() + 1);
			roomRepository.save(room);

		} catch (Exception e) {
			throw new BedServiceException(ErrorConstant.BED_SAVE_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public List<BedDto> getAllBeds() {
		List<Bed> beds = bedRepository.findAll();
		if (beds.isEmpty()) {
			throw new BedServiceException(ErrorConstant.BED_LIST_EMPTY, HttpStatus.NO_CONTENT);
		}

		List<BedDto> bedDtos = new ArrayList<>();
		for (Bed bed : beds) {

			BedDto bedDto = new BedDto();

			bedDto.setBedNo(bed.getBedNo());
			bedDto.setStatus(bed.getStatus());
			bedDto.setPrice(bed.getPrice());
			bedDto.setDeposit(bed.getDeposit());

			bedDtos.add(bedDto);
		}

		return bedDtos;
	}

	@Override
	public BedDto getBedById(int id) {
		Optional<Bed> optionalBed = bedRepository.findById(id);
		if (optionalBed.isEmpty()) {

			throw new BedServiceException(ErrorConstant.BED_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		Bed bed = optionalBed.get();

		BedDto bedDto = new BedDto();
		bedDto.setBedNo(bed.getBedNo());
		bedDto.setPrice(bed.getPrice());
		bedDto.setStatus(bed.getStatus());
		bedDto.setDeposit(bed.getDeposit());

		return bedDto;
	}

	@Override
	public void deleteAllBeds() {
		try {
			bedRepository.deleteAll();

			List<Room> rooms = roomRepository.findAll();
			for (Room room : rooms) {
				room.setSharing(0);
			}
			roomRepository.saveAll(rooms);

		} catch (Exception e) {
			throw new BedServiceException(ErrorConstant.BED_DELETE_ALL_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public void deleteBedById(int id) {

		Optional<Bed> optionalBed = bedRepository.findById(id);

		if (optionalBed.isEmpty()) {
			throw new BedServiceException(ErrorConstant.BED_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
		Bed bed = optionalBed.get();

		Room room = bed.getRoom();

		try {
			bedRepository.deleteById(id);

			int currentSharing = room.getSharing();
			if (currentSharing > 0) {
				room.setSharing(currentSharing - 1);
			} else {
				room.setSharing(0);
			}
			roomRepository.save(room);

		} catch (Exception e) {
			throw new BedServiceException(ErrorConstant.BED_DELETE_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public List<BedDto> getAvailableBedsByRoomSharing(int hostelId, int sharing) {

		List<Bed> beds = bedRepository.findAvailableBedsByRoomSharing(sharing, hostelId);

		if (beds.isEmpty()) {
			throw new BedServiceException(ErrorConstant.AVAILABLE_BEDS_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		List<BedDto> bedDtos = new ArrayList<>();

		for (Bed bed : beds) {
			try {

				Room room = bed.getRoom();
				Floor floor = room.getFloor();
				Building building = floor.getBuilding();
				Hostel hostel = building.getHostel();

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
				throw new BedServiceException(ErrorConstant.INVALID_BED_DATA, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		return bedDtos;
	}

}