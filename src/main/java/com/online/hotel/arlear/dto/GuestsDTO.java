package com.online.hotel.arlear.dto;

import java.util.List;

import lombok.Data;

@Data
public class GuestsDTO {
	private Long idReservation;
	private List<GuestDTO> guest;
}
