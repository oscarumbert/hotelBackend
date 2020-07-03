package com.online.hotel.arlear.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.online.hotel.arlear.enums.ReservationStatus;
import com.online.hotel.arlear.enums.ReservationType;
import com.online.hotel.arlear.enums.RoomAditionals;
import com.online.hotel.arlear.model.Contact;
import com.online.hotel.arlear.model.Room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Reservation2DTO {
	//private Long id;
	private RoomDTO room;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate beginDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate endDate;
	private String adultsCuantity;
	private String childsCuantity;
	private String reservationType;	
	private String aditionals;
	private ContactDTO contact;//(relacion contacto asociado)
	private String price;
	private String sign;//seña?
	private String reservationStatus;
	//private Payment payment;
	//private Long card;//(aca va una relacion con Card)
	
	/*@OneToOne(cascade= CascadeType.ALL)
	private Promotion idPromotion;*/

	
}