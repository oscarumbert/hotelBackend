package com.online.hotel.arlear.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCreateReservation extends ResponseDTO{
	private Long id;
	
	public ResponseCreateReservation(Long id, String status, String code, String description) {
		super(status,code,description);
		this.id = id;
	}
}
