package com.online.hotel.arlear.dto;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.online.hotel.arlear.enums.EventType;
import com.online.hotel.arlear.model.Contact;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventDTO {
	private String startDateHour;
	
	private String endDateHour;
	
	private String eventType;
	
	private Integer guests;
	
	@OneToOne(cascade= CascadeType.ALL)
	private Contact contact;
}
