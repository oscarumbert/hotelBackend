package com.online.hotel.arlear.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.online.hotel.arlear.enums.DocumentType;
import com.online.hotel.arlear.enums.GenderType;

import lombok.Data;

@Entity
@Data
public class Contact {
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO )
	private Long id;
	
	private String name;
	private String surname;
	
	@Enumerated(EnumType.STRING)
	private DocumentType documentType;
	
	private Integer documentNumber;
	
	@Enumerated(EnumType.STRING)
	private GenderType gender;
	
	private String mail;
	private Integer phone;
	
	@OneToOne (cascade= CascadeType.ALL)
	private Card card;
	
}
