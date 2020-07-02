package com.online.hotel.arlear.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.online.hotel.arlear.enums.TicketStatus;

import lombok.Data;

@Data
public class TicketDTOFind {
	private LocalDateTime date;
	private SubsidiaryDTO subsidiary;
	private String status;
	private ContactDTOOrder contact;
	private List<TransactionDTOFind> transaction;
}
