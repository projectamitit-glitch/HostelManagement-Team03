package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.HostelDto;

public interface HostelService {
	
	public void saveHostel(HostelDto hostelDto, int organizationId);
	
	public void deleteHostel(int id);
	public void deleteAllHostels();
	
	public HostelDto getHostel(int id);
	
	public List<HostelDto> getAllHostels();

}
