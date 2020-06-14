package com.online.hotel.arlear.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data

public class GuestDTO {

	private String name;
	private String surname;
	private String documentNumber;
}
