package com.online.hotel.arlear.dto;

import lombok.Data;

@Data
public class ContactDTOOrder {
	//private Long id;
	private String name;
	private String surname;
	private String documentType;
	private String documentNumber;
	private String gender;
	private String mail;
	private String phone;
	private CardDTO card;
}
