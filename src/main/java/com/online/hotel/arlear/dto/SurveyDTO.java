package com.online.hotel.arlear.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;


@Data
public class SurveyDTO {
	private String question;
	private Integer stars;
	private String answerDetails;
	private String client;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate date;
	private Long idReservation;
}
