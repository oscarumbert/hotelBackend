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
public class Guest {
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO )
	private Long id_guess;
	
	private String name;
	private String surname;
	private Double documentNumber;
	
	/*@OneToOne(cascade= CascadeType.ALL)
	private Reservation reservation;*/
}
