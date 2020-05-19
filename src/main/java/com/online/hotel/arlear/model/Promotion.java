package com.online.hotel.arlear.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Promotion {
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO )
	private Long id_promotion;
	
	private String description;
	private Integer price;//porcentaje de descuento?
	private LocalDate beginDate;
	private LocalDate endDate;
	
	

}
