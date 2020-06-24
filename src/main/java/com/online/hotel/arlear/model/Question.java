package com.online.hotel.arlear.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Question {
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO )
	private Long idQuestion;
	private String question;
}
