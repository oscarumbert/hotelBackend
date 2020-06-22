package com.online.hotel.arlear.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrderRestaurantDTO {
	private Long idReservation;
	private String orderType;
	private List<Integer> product;	
	private List<Integer> menu;	
}
