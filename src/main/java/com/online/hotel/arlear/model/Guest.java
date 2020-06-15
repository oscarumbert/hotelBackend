package com.online.hotel.arlear.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import lombok.Data;

@Entity
@Data
public class Guest {
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO )
	private Long id_guess;
	
	private String name;
	private String surname;
	private String documentNumber;
	
}
