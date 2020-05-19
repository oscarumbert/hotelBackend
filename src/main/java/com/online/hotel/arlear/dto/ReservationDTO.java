package com.online.hotel.arlear.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.online.hotel.arlear.enums.Payment;
import com.online.hotel.arlear.enums.ReservationType;
import com.online.hotel.arlear.enums.RoomAditionals;

import com.online.hotel.arlear.model.Contact;
//import com.online.hotel.arlear.model.Payment;
import com.online.hotel.arlear.model.Promotion;
import com.online.hotel.arlear.model.Room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReservationDTO {

	private Room room;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate beginDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate endDate;
	private Integer adultsCuantity;
	private Integer childsCuantity;
	private ReservationType reservationType;	
	private RoomAditionals aditionals;
	private Contact contact;//(relacion contacto asociado)
	private Double price;
	private Double sign;//seña?
	//private Payment payment;
	//private Long card;//(aca va una relacion con Card)
	
	/*@OneToOne(cascade= CascadeType.ALL)
	private Promotion idPromotion;*/

	
}



