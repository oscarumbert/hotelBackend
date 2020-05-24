package com.online.hotel.arlear.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ReservationFind {
	private Long id;
	private LocalDate beginDate;
}
