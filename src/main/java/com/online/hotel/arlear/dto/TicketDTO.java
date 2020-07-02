package com.online.hotel.arlear.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.online.hotel.arlear.enums.TicketStatus;

import lombok.Data;

@Data
public class TicketDTO {

	private SubsidiaryDTO subsidiary;
	private LocalDateTime date;
	private List<TransactiontDTO> transaction;
}
