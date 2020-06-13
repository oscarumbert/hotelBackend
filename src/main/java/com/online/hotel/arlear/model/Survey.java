package com.online.hotel.arlear.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.online.hotel.arlear.enums.Question;

import lombok.Data;

@Entity
@Data
public class Survey {
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO )
	private Long idSurvey;
	private Question question;
	private Integer stars;
	private String answerDetails;
	private String client;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate date;
}
