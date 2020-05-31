package com.online.hotel.arlear.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.online.hotel.arlear.enums.ProductAvailability;
import com.online.hotel.arlear.enums.ProductType;

import lombok.Data;

@Entity
@Data
public class Product {
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO )
	private Long id;	
	private String name;
	private Double price;
	
	@Enumerated(EnumType.STRING)
	private ProductType productType;
	
	@Enumerated(EnumType.STRING)
	private ProductAvailability productAvailability;

}
