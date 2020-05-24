package com.online.hotel.arlear.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.online.hotel.arlear.enums.ProductType;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDTO {	
	private String name;
	private String code;
	private String productType;
	private String price; 
}