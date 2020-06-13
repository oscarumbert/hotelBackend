package com.online.hotel.arlear.dto;

import java.util.List;

import com.online.hotel.arlear.model.Room;

import lombok.Data;

@Data
public class FloorDTO {

	private Integer floor;
	private List<Room> rooms;
}
