package com.online.hotel.arlear.dto;

import java.util.List;

import lombok.Data;
@Data
public class MenuDTOFindUnity {

	private Long idMenu;
	private String nameMenu;
	private Double priceTotal;
	private Integer discount;
	private String menutype;
	private String menustate;
	private List<ProductDTO> producto;
}
