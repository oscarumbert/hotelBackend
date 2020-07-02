package com.online.hotel.arlear.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrderOpensDTO {
	private Long idOrder;
	private Double priceTotal;
	private String orderType;
	private String orderStatus;
	private List<ProductDTO> product;	
	private List<MenuDTOOrder> menu;
	private Long numberReservation;
	private RoomDTOOrder room; 
	private ContactDTOOrder client;
}
