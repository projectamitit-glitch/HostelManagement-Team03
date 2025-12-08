package com.example.demo.dto;

import lombok.Data;

@Data
public class BedDto {

	private int bedNo;
	private String status;
	private int price;
	private int deposit;

	private int roomNo;
	private int floorNo;
	private String buildingName;
	private String hostelName;

}
