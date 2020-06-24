package com.online.hotel.arlear.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.online.hotel.arlear.model.Answer;
import com.online.hotel.arlear.model.Question;

import lombok.Data;


@Data
public class SurveyDTO {
	private String client;
	private Integer idQuestion;
	private Integer stars;
	private String answerDetails;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate date;
	private Long idReservation;
}
