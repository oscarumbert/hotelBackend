package com.online.hotel.arlear.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContactFindDTO {
	private String documentType;
	private String documentNumber;
	private String gender;
}
