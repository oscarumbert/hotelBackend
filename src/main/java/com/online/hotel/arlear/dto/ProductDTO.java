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
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO )
	private Long id;	
	private String name;
	private int code;
	private ProductType productType;
	private int price; 
}