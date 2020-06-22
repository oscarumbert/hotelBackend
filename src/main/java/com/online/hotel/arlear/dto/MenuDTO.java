package com.online.hotel.arlear.dto;

import java.util.List;

import lombok.Data;

@Data
public class MenuDTO {
	private String nameMenu;
	private Double discount;
	private String menutype;
	private String menustate;
	private List<Integer> producto;
}

