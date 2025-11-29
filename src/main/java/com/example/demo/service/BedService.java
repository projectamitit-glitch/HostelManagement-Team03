package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.BedDto;

public interface BedService {

	void addBed(BedDto bedDto);

	List<BedDto> getAllBeds();

	BedDto getBedById(int id);

	void deleteAllBeds();

	void deleteBedById(int id);
}
