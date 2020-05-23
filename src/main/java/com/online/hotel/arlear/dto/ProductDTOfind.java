package com.online.hotel.arlear.dto;

import com.online.hotel.arlear.enums.ProductType;

import lombok.Data;

@Data
public class ProductDTOfind {
	private String name;
	private int code;
	private ProductType productType;
}


