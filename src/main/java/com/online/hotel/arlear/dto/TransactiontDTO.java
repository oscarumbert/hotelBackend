package com.online.hotel.arlear.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.online.hotel.arlear.enums.Section;

import lombok.Data;

@Data
public class TransactiontDTO {

	private Integer document;
	private Double amount;
	private String element;
	private String description;
	private Section section;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime date;
}
