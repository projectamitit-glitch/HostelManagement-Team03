package com.example.demo.dto;

import java.time.LocalDate;
import lombok.Data;
@Data
public class BreakupDto {
	private int price;
	private int duration;
	private int finalAmount;
	private int bedNo;
	private int roomNo;
	private int floorNo;
	private int buildingId;
	private int hostelId;
	private String hostelName;
	private LocalDate startDate;
	private LocalDate endDate;
	public BreakupDto(int price, int duration, int finalAmount, int bedNo, int roomNo, int floorNo, int buildingId,
			int hostelId, String hostelName,LocalDate startDate, LocalDate endDate) {
		super();
		this.price = price;
		this.duration = duration;
		this.finalAmount = finalAmount;
		this.bedNo = bedNo;
		this.roomNo = roomNo;
		this.floorNo = floorNo;
		this.buildingId = buildingId;
		this.hostelId = hostelId;
		this.hostelName = hostelName;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
}
	
	
	