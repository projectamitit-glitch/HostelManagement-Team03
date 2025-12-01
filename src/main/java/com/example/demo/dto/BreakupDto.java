package com.example.demo.dto;

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
	public BreakupDto(int price, int duration, int finalAmount, int bedNo, int roomNo, int floorNo, int buildingId,
			int hostelId, String hostelName) {
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
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getFinalAmount() {
		return finalAmount;
	}
	public void setFinalAmount(int finalAmount) {
		this.finalAmount = finalAmount;
	}
	public int getBedNo() {
		return bedNo;
	}
	public void setBedNo(int bedNo) {
		this.bedNo = bedNo;
	}
	public int getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}
	public int getFloorNo() {
		return floorNo;
	}
	public void setFloorNo(int floorNo) {
		this.floorNo = floorNo;
	}
	public int getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(int buildingId) {
		this.buildingId = buildingId;
	}
	public int getHostelId() {
		return hostelId;
	}
	public void setHostelId(int hostelId) {
		this.hostelId = hostelId;
	}
	public String getHostelName() {
		return hostelName;
	}
	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}

	
}
