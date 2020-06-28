package com.online.hotel.arlear.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContactDTO {
	private String idReservation;
	private String idEvent;
	private String name;
	private String surname;
	private String documentType;
	private String documentNumber;
	private String gender;
	private String mail;
	private String phone;
	private CardDTO card;
	
}
