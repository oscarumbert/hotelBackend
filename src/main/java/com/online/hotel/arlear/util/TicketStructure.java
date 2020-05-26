package com.online.hotel.arlear.util;

import java.util.List;

import com.online.hotel.arlear.model.StructureItem;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketStructure {

	private List<StructureItem> items;
	private Double subtotalItems;
	private Double total;
	

}
