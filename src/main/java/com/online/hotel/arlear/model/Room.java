package com.online.hotel.arlear.model;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//import com.online.hotel.arlear.enums.RoomAditionals;
import com.online.hotel.arlear.enums.RoomCategory;
import com.online.hotel.arlear.enums.RoomStatus;
//import com.online.hotel.arlear.enums.RoomServices;
import com.online.hotel.arlear.enums.RoomType;

import lombok.Data;

@Entity
@Data
public class Room {
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO )
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private RoomCategory category;
	
	@Enumerated(EnumType.STRING)
	private RoomType type;
	
	private Integer capacity; //cantidad de camas
	private Integer roomNumber;
	private Integer floor;
	private Integer price;
	
	@Enumerated(EnumType.STRING)
	private RoomStatus roomStatus;
	
}
