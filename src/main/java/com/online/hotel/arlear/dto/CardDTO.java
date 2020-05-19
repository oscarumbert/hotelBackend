package com.online.hotel.arlear.dto;

import java.time.LocalDate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.online.hotel.arlear.enums.CardType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CardDTO {
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO )
	private Long id_card;
	
	private CardType cardType;
	private String nameOwner;
	private Integer cardNumber;
	private Integer codeSecurity;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate expirationDate;
}
