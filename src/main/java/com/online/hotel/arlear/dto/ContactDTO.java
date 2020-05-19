package com.online.hotel.arlear.dto;

import java.time.LocalDate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.online.hotel.arlear.enums.DocumentType;
import com.online.hotel.arlear.enums.GenderType;
import com.online.hotel.arlear.enums.ReservationType;
import com.online.hotel.arlear.enums.RoomAditionals;
import com.online.hotel.arlear.model.Card;
import com.online.hotel.arlear.model.Ticket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContactDTO {
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO )
	private Long id_contact;	
	private String name;
	private String surname;
	private DocumentType documentType;
	private Integer documentNumber;
	private GenderType sex;
	private String mail;
	private Integer phone;
	private Card card;
	//private Ticket ticket;
	
}
