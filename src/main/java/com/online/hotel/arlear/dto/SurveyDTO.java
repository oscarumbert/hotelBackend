package com.online.hotel.arlear.dto;

import lombok.Data;

@Data
public class SurveyDTO {
	private String question;
	private String stars;
	private String answerDetails;
	private String client;
	private String date;
}
