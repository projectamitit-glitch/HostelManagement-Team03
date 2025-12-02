

package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Bed;
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
	public void addBed(BedDto bedDto) {
		Room room = roomRepository.findById(bedDto.getRoomId()).orElse(null);
		if (room == null) {
			throw new BedServiceException(ErrorConstant.ROOM_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		Bed bed = new Bed();
		bed.setBedNo(bedDto.getBedNo());
		bed.setPrice(bedDto.getPrice());
		bed.setStatus(bedDto.getStatus());
		bed.setRoom(room);

		try {
			bedRepository.save(bed);
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

			bedDto.setId(bed.getId());
			bedDto.setBedNo(bed.getBedNo());
			bedDto.setStatus(bed.getStatus());
			bedDto.setPrice(bed.getPrice());
			bedDto.setRoomId(bed.getRoom().getId());

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
		bedDto.setId(bed.getId());
		bedDto.setBedNo(bed.getBedNo());
		bedDto.setPrice(bed.getPrice());
		bedDto.setStatus(bed.getStatus());
		bedDto.setRoomId(bed.getRoom().getId());

		return bedDto;
	}

	@Override
	public void deleteAllBeds() {
		try {
			bedRepository.deleteAll();
		} catch (Exception e) {
			throw new BedServiceException(ErrorConstant.BED_DELETE_ALL_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public void deleteBedById(int id) {

		if (bedRepository.findById(id).isEmpty()) {
			throw new BedServiceException(ErrorConstant.BED_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		try {
			bedRepository.deleteById(id);
		} catch (Exception e) {
			throw new BedServiceException(ErrorConstant.BED_DELETE_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}