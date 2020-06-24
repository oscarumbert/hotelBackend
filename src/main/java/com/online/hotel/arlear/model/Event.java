package com.online.hotel.arlear.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.online.hotel.arlear.enums.EventType;

import lombok.Data;

@Entity
@Data
public class Event {
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO )
	private Long idEvent;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime startDateHour;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime endDateHour;
	
	private EventType eventType;
	
	private Integer guests;
}
