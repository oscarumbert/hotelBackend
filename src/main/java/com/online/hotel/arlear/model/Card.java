package com.online.hotel.arlear.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.online.hotel.arlear.enums.CardType;

import lombok.Data;

@Entity
@Data
public class Card {
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO )
	private Long id_card;
	
	@Enumerated(EnumType.STRING)
	private CardType cardType;

	private String nameOwner;
	private String cardNumber;
	private Integer codeSecurity;
	private LocalDate expirationDate;
	
	
	
	
}
