package com.online.hotel.arlear.dto;

import lombok.Data;
@Data
public class RoomDTO {
	
	private String category;
	private String type;
	private String capacity; //cantidad de camas
	private String roomNumber;
	private String floor;
	private String price;
	private String roomStatus;
}
