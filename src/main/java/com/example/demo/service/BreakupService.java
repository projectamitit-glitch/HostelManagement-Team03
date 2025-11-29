package com.example.demo.service;

import com.example.demo.dto.BreakupDto;

public interface BreakupService {

	BreakupDto getByBedIdAndDuration(int id, int duration);
}
