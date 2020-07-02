package com.online.hotel.arlear.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Answer {
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO )
	private Long idAnswer;
	private String question;
	private Integer stars;
	private String answerDetails;
}
