package com.online.hotel.arlear.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Entity
@Data
public class Survey {
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO )
	private Long idSurvey;
	private String client;
	
	@ManyToMany (cascade= CascadeType.ALL)
	private List<Answer> answer;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate date;
	
	private Long idReservation;
}
