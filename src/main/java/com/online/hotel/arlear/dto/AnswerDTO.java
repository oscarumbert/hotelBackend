package com.online.hotel.arlear.dto;

import javax.persistence.CascadeType;
import javax.persistence.OneToOne;

import com.online.hotel.arlear.model.Question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AnswerDTO {
	private Question question;
	private Integer stars;
	private String answerDetails;
}
