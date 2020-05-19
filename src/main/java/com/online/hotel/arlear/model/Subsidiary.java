package com.online.hotel.arlear.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Subsidiary {
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO )
	private Long id;
	
	private Integer cuit;
	private String name;
	
	@OneToOne(cascade= CascadeType.ALL)
	private Address address;
	
	private String mail;
	private Integer phone;
}
