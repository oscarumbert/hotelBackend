package com.online.hotel.arlear.util;

import java.util.List;

import com.online.hotel.arlear.model.StructureItem;
import com.online.hotel.arlear.model.Subsidiary;

import lombok.Data;

@Data
public class TicketStructure {

	private String clientName;
	private String document;
	private String number;
	private Subsidiary subsidiary;
	private List<StructureItem> items;
	private Double subtotalHotel;
	private Double subtotalRestaurant;
	private Double subtotalSaloon;
	private Double total;
	

}
