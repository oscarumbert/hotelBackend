package com.online.hotel.arlear.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class TicketDTOFind {
	private SubsidiaryDTO subsidiary;
	private LocalDateTime date;
	private List<TransactiontDTO> transaction;
	private ContactDTO contact;
}
