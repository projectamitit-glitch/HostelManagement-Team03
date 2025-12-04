package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.RoomDto;

public interface RoomService {
	
	public void addRoom(RoomDto roomDto,int floorId);
	
	
	public List<RoomDto> getAllRooms();
	
	
	public RoomDto getRoom(int id);
	
	
	public void deleteRoom(int id);
	
	public void deleteAll();
	
	
	
	

}
