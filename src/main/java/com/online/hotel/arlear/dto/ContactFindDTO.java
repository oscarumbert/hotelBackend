package com.online.hotel.arlear.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactFindDTO {
	private String documentType;
	private String documentNumber;
	private String gender;
}
