package com.online.hotel.arlear.dto;

import lombok.Data;

@Data
public class ReservationOpenDTO {
	
	private Long id;
	private RoomDTO room;
	private ContactDTOOrder contact;

}
