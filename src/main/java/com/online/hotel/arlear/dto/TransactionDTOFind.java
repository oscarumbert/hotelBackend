package com.online.hotel.arlear.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.online.hotel.arlear.enums.Section;

import lombok.Data;

@Data
public class TransactionDTOFind {
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime date;

	private Section section;
	private Long numberSection;
	private String element;
	private Double amount;
	private String description;
	private String transactionStatus;

	
}
