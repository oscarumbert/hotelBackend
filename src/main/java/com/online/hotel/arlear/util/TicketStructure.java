package com.online.hotel.arlear.util;

import java.util.List;

import com.online.hotel.arlear.model.StructureItem;

import lombok.Data;

@Data
public class TicketStructure {

	private List<StructureItem> ItemsHotel;
	private Double TotalItemsHotel;
	private List<StructureItem> ItemsRestaurant;
	private Double TotalItemsRestaurant;
	private List<StructureItem> ItemsSaloon;
	private Double TotalItemsSaloon;
	

}
