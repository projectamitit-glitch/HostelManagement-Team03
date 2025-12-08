package com.example.demo.service;

import java.time.LocalDate;

import com.example.demo.dto.BreakupDto;

public interface BreakupService {

//	BreakupDto getByBedIdAndDuration(int id, int duration);
	
	BreakupDto getBreakup(int bedId,LocalDate startDate,int duration);
}
