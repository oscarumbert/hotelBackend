package com.online.hotel.arlear.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.online.hotel.arlear.enums.Section;

import lombok.Data;

@Data
@Entity
public class Transaction {
	
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO )
	private Long id;
	private Double amount;
	private String element;
	private String description;
	
	@Enumerated(EnumType.STRING)
	private Section section;
	
	private LocalDateTime date;
}
