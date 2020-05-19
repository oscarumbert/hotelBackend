package com.online.hotel.arlear.dto;

import com.online.hotel.arlear.enums.RoomCategory;
import com.online.hotel.arlear.enums.RoomStatus;
import com.online.hotel.arlear.enums.RoomType;

import lombok.Data;
@Data
public class RoomDTO {
	
	private RoomCategory category;
	private RoomType type;
	private Integer capacity; //cantidad de camas
	private Integer roomNumber;
	private Integer floor;
	private Integer price;
	private RoomStatus roomStatus;
	//private RoomCategory roomCategory;
	//private RoomType roomType;
	//private RoomAditionals roomAditionals;
	//private RoomServices roomServices;
}
