package com.online.hotel.arlear.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Answer {
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO )
	private Long idAnswer;
	@OneToOne (cascade= CascadeType.ALL)
	private Question question;
	private Integer stars;
	private String answerDetails;

}
